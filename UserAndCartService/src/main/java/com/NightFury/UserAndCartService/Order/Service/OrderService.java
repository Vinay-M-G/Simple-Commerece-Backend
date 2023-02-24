package com.NightFury.UserAndCartService.Order.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.json.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.UserAndCartService.UserAndCartServiceApplication;
import com.NightFury.UserAndCartService.Cart.Entity.CartProductEntryModel;
import com.NightFury.UserAndCartService.Cart.Repository.CartProductEntryRepository;
import com.NightFury.UserAndCartService.Cart.Repository.CartUserAssociationRepository;
import com.NightFury.UserAndCartService.Cart.Service.CartServiceProvider;
import com.NightFury.UserAndCartService.JsonReader.GlobalJsonReader;
import com.NightFury.UserAndCartService.Order.Entity.OrderEntryModel;
import com.NightFury.UserAndCartService.Order.ExceptionsHandler.OrderNotFound;
import com.NightFury.UserAndCartService.Order.Repository.CartOrderAssociationRepository;
import com.NightFury.UserAndCartService.Order.Service.impl.OrderPopulator;
import com.NightFury.UserAndCartService.Order.Service.impl.OrderProcessHandler;
import com.NightFury.UserAndCartService.Order.Service.impl.OrderValidationDynamicHandler;
import com.NightFury.UserAndCartService.Proxy.ProductDetailsProvider;
import com.NightFury.UserAndCartService.Support.SupportFunctionsProvider;

@Service
public class OrderService {
	
	private static Logger logger = LoggerFactory.getLogger(UserAndCartServiceApplication.class);
	private static String ORDER_CREATED_SUCCESSFULLY = "Order Created Successfully !!!";
	private static String INVALID_PRICE = "Price cannot be less than INR 1";
	private static String ORDER_EXIST = "Order Exists for the given cart";
	private static String REQUEST_QTY_NOT_AVAILABLE =" -> Requested quantity is not available !!! available quantity is : ";
	private static String NOT_AVAILABLE_ANYMORE = " -> Not Available Anymore For Sale !!!";
	private static String CHANGE_IN_CART_PRICE = "Price for Product or Service has changed, please review";
	
	private String orderId;
	private String emailId;
	
	@Autowired
	GlobalJsonReader jsonReader;
	
	@Autowired
	ProductDetailsProvider productDetailsProvider;
	
	@Autowired
	CartProductEntryRepository cartProductEntryRepository;
	
	@Autowired
	SupportFunctionsProvider supportFunctionsProvider;
	
	@Autowired
	OrderValidationDynamicHandler orderValidationDynamicHandler;
	
	@Autowired
	CartServiceProvider cartServiceProvider;
	
	@Autowired
	OrderProcessHandler orderProcessHandler;
	
	@Autowired
	CartUserAssociationRepository cartUserAssociationRepository;
	
	@Autowired
	OrderPopulator orderPopulator;
	
	private Map<String, Object> validateCart(List<JsonObject> products , List<CartProductEntryModel> cartProducts, String guid, double payablePrice, Map<String, Object> basket) {
		Map<String , Object> response = new HashMap<>();
		
		if(payablePrice < 1) {
			response = supportFunctionsProvider.responseBuilderForCartUpdate(true, INVALID_PRICE);
			return response;
		}
		
		if(orderValidationDynamicHandler.isOrderExist(guid)) {
			logger.warn("Order already Exists for given guid : " + guid);
			response = supportFunctionsProvider.responseBuilderForCartUpdate(true, ORDER_EXIST);
			return response;
		}
		
		for(JsonObject product : products) {
			
			if(!product.getJsonObject("availabiltyOptions").getBoolean("isSellableOnline")) {
				
				logger.warn("Availability validation failed for guid : " + guid);
				cartProductEntryRepository.removeProductEntry(guid, product.getString("productId"));
				response = supportFunctionsProvider.responseBuilderForCartUpdate(true, product.getString("productName") + NOT_AVAILABLE_ANYMORE);
				return response;
			}
			
			
			int availableQuantity = product.getJsonObject("stockData").getJsonNumber("quantity").intValue();
			
			if(!orderValidationDynamicHandler.validateCartForAvailableQuantity(product.getString("productId"), cartProducts, availableQuantity)){
				
				logger.warn("Stock validation failed for guid : " + guid);
				cartProductEntryRepository.updateProductQuantity(availableQuantity, guid, product.getString("productId"));
				response = supportFunctionsProvider.responseBuilderForCartUpdate(true, product.getString("productName") + REQUEST_QTY_NOT_AVAILABLE + availableQuantity);
				return response;
			}
		}
		
		if(!orderValidationDynamicHandler.validateCartForPriceChange(payablePrice, basket)) {
			
			logger.warn("Price validation failed for guid : " + guid);
			response = supportFunctionsProvider.responseBuilderForCartUpdate(true, CHANGE_IN_CART_PRICE);
			return response;
		}
		
		logger.info("Cart Validation succeeded for guid : " + guid);
		
		return response;
	}
	
	private Map<String, Object> saveOrder(String guid, Map<String, Object> basket) {
		
		Map<String , Object> response = new HashMap<>();
		this.emailId = cartUserAssociationRepository.findById(guid).get().getUserId();
		
		if(this.emailId != null) {
			
			try {
				
				this.orderId = orderProcessHandler.createOrderId(guid, emailId);
				logger.info("Order Id : " + this.orderId + " created successfully for guid : " + guid);
				
			}catch(Exception ex) {

				logger.error("An Exception occured while saving order base table " + ex.toString());
				return response;
			}
			
		}
		
		if(this.orderId != null) {
			
			try {
				
				boolean productUpdateStatus = orderProcessHandler.createOrderEntries(guid, orderId, basket);
				
				if(productUpdateStatus){
					
					logger.info("Order entries was saved successfully for order id : " + this.orderId);
				}
				
			}catch(ClassCastException ex) {
				
				String errorMessage = "An Error occured while parsing cart product and service data for guid : " + guid;
				logger.error(errorMessage + ex.toString());
				response =  supportFunctionsProvider.responseBuilderForCartUpdate(true, errorMessage);
				return response;
				
			}catch(Exception ex) {
				
				String errorMessage = "An Un-Expected error occured while updating order entries for guid : " + guid;
				logger.error(errorMessage + ex.toString());
				response =  supportFunctionsProvider.responseBuilderForCartUpdate(true, errorMessage);
				return response;
			}
		
		
		}
		
		//Block to update order cost summary
		try {
			
			boolean cartSummaryUpdateStatus = orderProcessHandler.updateOrderCostSummary(guid, orderId, basket);
			
			if(cartSummaryUpdateStatus) {
				
				logger.info("Cart Prices for order : " + this.orderId + " are updated to DB successfully");
			}
			
		}catch(ClassCastException ex) {
			
			String errorMessage = "An Error occured while parsing cart cost summary data for guid : " + guid;
			logger.error(errorMessage + ex.toString());
			response =  supportFunctionsProvider.responseBuilderForCartUpdate(true, errorMessage);
			return response;
			
		}catch(Exception ex) {
			
			String errorMessage = "An Un-Expected error occured while saving cost summart for guid : " + guid;
			logger.error(errorMessage + ex.toString());
			response =  supportFunctionsProvider.responseBuilderForCartUpdate(true, errorMessage);
			return response;
		}
		
		
		//Block for Order Address update
		try {
			
			boolean orderAddressUpdateStatus = orderProcessHandler.createAddressEntries(guid, orderId, emailId);
			
			if(orderAddressUpdateStatus) {
				
				logger.info("Address details for order : " + this.orderId + " is updated to DB successfully");
			}
			
		}catch(ClassCastException ex) {
			
			String errorMessage = "An Error occured while updating Address details for guid : " + guid;
			logger.error(errorMessage + ex.toString());
			response =  supportFunctionsProvider.responseBuilderForCartUpdate(true, errorMessage);
			return response;
			
		}catch(Exception ex) {
			
			String errorMessage = "An Un-Expected error occured while saving Address details for guid : " + guid;
			logger.error(errorMessage + ex.toString());
			response =  supportFunctionsProvider.responseBuilderForCartUpdate(true, errorMessage);
			return response;
		}
		
		//Block to update Cart User Association Model 
		try {

			boolean cartUserUpdateStatus = orderProcessHandler.updateCartUserAssociation(guid);

			if (cartUserUpdateStatus) {

				logger.info("Cart User Association Model is made to false for guid : " + guid);
			}

		} catch (ClassCastException ex) {

			String errorMessage = "An Error occured while updating Cart User Association Model for guid : " + guid;
			logger.error(errorMessage + ex.toString());
			response = supportFunctionsProvider.responseBuilderForCartUpdate(true, errorMessage);
			return response;

		} catch (Exception ex) {

			String errorMessage = "An Un-Expected error occured while updating Cart User Association Model for guid : "
					+ guid;
			logger.error(errorMessage + ex.toString());
			response = supportFunctionsProvider.responseBuilderForCartUpdate(true, errorMessage);
			return response;
		}
		
		
		return response;
	}
	
	public Map<String, Object> createOrderAction(String guid, double payablePrice){
		
		Map<String, Object> response = new HashMap<String, Object>();
		List<JsonObject> productObjects = new ArrayList<JsonObject>();
		
		List<CartProductEntryModel> productsInCart = cartProductEntryRepository.findByGuid(guid);
		
		productsInCart.stream().forEach(element -> {
			String productResponse = productDetailsProvider.getProduct(element.getProductCode());
			productObjects.add(jsonReader.sendJsonObjectFromString(productResponse));
		});
		
		Map<String , Object> basket = cartServiceProvider.getCartDetails(guid);
		
		response = validateCart(productObjects, productsInCart, guid, payablePrice, basket);
		
		if(!response.isEmpty()) {
			return response;
		}
		
		response = saveOrder(guid, basket);
		
		if(response.isEmpty()) {
			
			response = supportFunctionsProvider.responseBuilderForCartUpdate(false, ORDER_CREATED_SUCCESSFULLY);
			response.put("orderId", this.orderId);
		}
		
		return response;
		
	}
	
	public Map<String, Object> isValidCartId(String guid) {
		
		if(orderValidationDynamicHandler.isACart(guid)) {
			return supportFunctionsProvider.responseBuilderForCartUpdate(false, "Cart Found");
		}else {
			return supportFunctionsProvider.responseBuilderForCartUpdate(true, "Cart Not Found !!!");
		}
		 
	}
	
	public Map<String, Object> reteriveOrderDetails(String orderId){
		Map<String , Object> response = new HashMap<>();
		
		try {
			response = supportFunctionsProvider.responseBuilderForCartUpdate(false, "Order Found");
			response.put("orderAddressDetails", orderPopulator.getOrderAddressEntries(orderId));
			response.put("OrderCostSummary", orderPopulator.getOrderCostSummary(orderId));
			response.put("OrderEntries", orderPopulator.getOrderEntries(orderId));
			response.put("OrderSummary", orderPopulator.getCartOrderAssociationModel(orderId));	
			
		}catch(OrderNotFound ex) {
			
			logger.error("Order Id : " + orderId + " Not Found");
			response = supportFunctionsProvider.responseBuilderForCartUpdate(true, ex.toString());
			
		}catch(Exception ex) {
			
			logger.error("An unexpected error occured while fetching order details for order Id : " + orderId);
			response = supportFunctionsProvider.responseBuilderForCartUpdate(true, ex.toString());
		}
		
		logger.info("Order details sent for order id : " + orderId);
		return response;
	}
}

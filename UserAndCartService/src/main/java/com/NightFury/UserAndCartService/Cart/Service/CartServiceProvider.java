package com.NightFury.UserAndCartService.Cart.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.UserAndCartService.Cart.Entity.CartCostModel;
import com.NightFury.UserAndCartService.Cart.Service.impl.CartCostDynamicCompute;
import com.NightFury.UserAndCartService.Cart.Service.impl.CartDataCustomisationHandler;
import com.NightFury.UserAndCartService.Cart.Service.impl.CartDataHandler;
import com.NightFury.UserAndCartService.Cart.Service.impl.CartEntryCreationHandler;
import com.NightFury.UserAndCartService.Cart.Service.impl.ComputeCartCount;
import com.NightFury.UserAndCartService.Support.SupportFunctionsProvider;

@Service
public class CartServiceProvider {
	
	private static final String NO_PRODUCTS_IN_CART = "Empty Cart";
	private static final String VALID_CART = "Valid Cart with products";
	
	@Autowired
	CartEntryCreationHandler cartEntryCreationHandler;
	
	@Autowired
	CartDataHandler cartDataHandler;
	
	@Autowired
	CartDataCustomisationHandler cartDataCustomisationHandler;
	
	@Autowired
	CartCostDynamicCompute cartCostDynamicCompute;
	
	@Autowired
	ComputeCartCount computeCartCount;
	
	@Autowired
	SupportFunctionsProvider supportFunctionsProvider;
	
	public Map<String, Object> createProductCartEntry(String productCode, String guid){
		return cartEntryCreationHandler.addProduct(productCode, guid);
	}
	
	public Map<String , Object> updateProductServiceEntry(String productCode, String serviceId, String guid, boolean selected){
		if(selected) {
			return cartEntryCreationHandler.addService(productCode, guid, serviceId);
		}else {
			return cartEntryCreationHandler.removeService(productCode, guid, serviceId);
		}
	}
	
	public Map<String , Object> updateDeliveryServiceEntry(String serviceId, String guid){
		return cartEntryCreationHandler.addDeliveryService(serviceId, guid);
	}
	
	public Map<String, Object> updateProductQuantity(String productCode, String guid, int quantity){
		return cartEntryCreationHandler.updateProduct(productCode, guid, quantity);
	}
	
	public Map<String, Object> getCartDetails(String guid){
		Map<String , Object> response = new HashMap<>();
		List<Map<String , Object >> cartEntries = new ArrayList<>();
		List<Map<String , Object >> deliveryEntries = new ArrayList<>();
		
		List<String> products = cartDataHandler.getProductsInCart(guid);
		
		if(products.size() != 0) {
			
			response = supportFunctionsProvider.responseBuilderForCartUpdate(false, VALID_CART);
			products.stream().forEach( element -> {
				JsonObject productDetail = cartDataHandler.getProductDetailForCart(element);
				JsonArray serviceDetails = cartDataHandler.getServiceDetailForProduct(element);
				
				Map<String , Object> productEntry = cartDataCustomisationHandler.productMapper(productDetail, serviceDetails, guid);
				cartEntries.add(productEntry);
				
			});
			
			response.put("products", cartEntries);
			
			JsonArray serviceDetails = cartDataHandler.getDeliveryServiceForProducts(products);
			int cartTotalQuantity = computeCartCount.getCartQuantity(cartEntries);
			deliveryEntries = cartDataCustomisationHandler.serviceMapper(serviceDetails, guid, null, cartTotalQuantity);
			response.put("basketDeliveryServices", deliveryEntries);
			
			CartCostModel cartCostModel = cartCostDynamicCompute.computeCartCost(cartEntries, deliveryEntries);
			Map<String , Double> cartPriceEntries = cartCostDynamicCompute.cartCostMap(cartCostModel);
			
			response.put("basketCostSummary", cartPriceEntries);
			return response;
		}
		
		
		return supportFunctionsProvider.responseBuilderForCartUpdate(true, NO_PRODUCTS_IN_CART);
	}
}

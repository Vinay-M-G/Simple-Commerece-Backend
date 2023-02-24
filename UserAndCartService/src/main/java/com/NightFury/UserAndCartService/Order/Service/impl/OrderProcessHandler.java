package com.NightFury.UserAndCartService.Order.Service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.UserAndCartService.Cart.Repository.CartUserAssociationRepository;
import com.NightFury.UserAndCartService.Order.Entity.CartOrderAssociationModel;
import com.NightFury.UserAndCartService.Order.Entity.OrderAddressModel;
//import com.NightFury.UserAndCartService.Order.Entity.OrderAddressModel;
import com.NightFury.UserAndCartService.Order.Entity.OrderCostSummaryModel;
import com.NightFury.UserAndCartService.Order.Entity.OrderEntryModel;
import com.NightFury.UserAndCartService.Order.Repository.CartOrderAssociationRepository;
import com.NightFury.UserAndCartService.Order.Repository.OrderAddressRepository;
import com.NightFury.UserAndCartService.Order.Repository.OrderCostSummaryRepository;
import com.NightFury.UserAndCartService.Order.Repository.OrderEntryRepository;
import com.NightFury.UserAndCartService.Order.Service.dao.OrderProcessor;
import com.NightFury.UserAndCartService.Support.SupportFunctionsProvider;
import com.NightFury.UserAndCartService.User.AddressService.impl.UserAddressHandler;
import com.NightFury.UserAndCartService.User.Entity.UserAddressModel;

@Service
public class OrderProcessHandler implements OrderProcessor {
	
	@Autowired
	CartOrderAssociationRepository cartOrderAssociationRepository;
	
	@Autowired
	OrderAddressRepository orderAddressRepository;
	
	@Autowired
	OrderCostSummaryRepository orderCostSummaryRepository;
	
	@Autowired
	OrderEntryRepository orderEntryRepository;
	
	@Autowired
	SupportFunctionsProvider supportFunctionsProvider;
	
	@Autowired
	UserAddressHandler userAddressHandler;
	
	@Autowired
	CartUserAssociationRepository cartUserAssociationRepository;
	
	private String generateServiceEntryId(int productEntryIndex, int serviceEntryIndex) {
		
		int characterStartCount = 64;
		String temp = Character.toString(characterStartCount + serviceEntryIndex);
		return Integer.toString(productEntryIndex) + temp;
	}
	
	private String generateOrderId() {
		String part1 = Integer.toString( new Random().nextInt(999 - 100) + 100);
		String part2 = Integer.toString( new Random().nextInt(9999 - 1000) + 1000);
		String part3 = Integer.toString( new Random().nextInt(999 - 100) + 100);
		return part1 + "-" + part2 + "-" + part3;
	}
	
	@Override
	public String createOrderId(String guid, String emailId) {
		String orderId = generateOrderId();
		
		CartOrderAssociationModel cartOrderAssociationModel = new CartOrderAssociationModel();
		cartOrderAssociationModel.setOrderId(orderId);
		cartOrderAssociationModel.setCreatedAt(supportFunctionsProvider.getCurrentDateTime());
		cartOrderAssociationModel.setGuid(guid);
		cartOrderAssociationModel.setEmailId(emailId);
		
		cartOrderAssociationRepository.saveAndFlush(cartOrderAssociationModel);
		
		return orderId;
	}

	@Override
	public boolean createOrderEntries(String guid, String orderId, Map<String, Object> basket) {
		
		List<OrderEntryModel> orderEntries = new ArrayList<OrderEntryModel>();
		AtomicInteger index = new AtomicInteger();
					
		List<Map<String, Object>> products = (List<Map<String, Object>>) basket.get("products");

		products.stream().forEach(element -> {

			OrderEntryModel orderEntryModel = new OrderEntryModel();

			orderEntryModel.setQuantity((int) element.get("quantity"));
			orderEntryModel.setEntryCode((String) element.get("productId"));
			orderEntryModel.setProductModelId((String) element.get("productModelId"));
			orderEntryModel.setCodeShortDescription((String) element.get("productName"));
			orderEntryModel.setOrderId(orderId);
			orderEntryModel.setEntryId(Integer.toString(index.incrementAndGet()));
			orderEntryModel.setPk(supportFunctionsProvider.generateUUID());

			Map<String, Double> priceElements = (Map<String, Double>) element.get("priceValues");
			
			if(priceElements.get("offerPrice") != null) {
				
				orderEntryModel.setPayablePrice(priceElements.get("offerPrice"));
				orderEntryModel.setBasePrice(priceElements.get("basePrice"));
				orderEntryModel.setLineTotal(priceElements.get("lineTotal"));
			}else {
				
				orderEntryModel.setPayablePrice(priceElements.get("basePrice"));
				orderEntryModel.setBasePrice(priceElements.get("basePrice"));
				orderEntryModel.setLineTotal(priceElements.get("lineTotal"));
			}
			
			orderEntries.add(orderEntryModel);
			
			List<Map<String, Object>> serviceEntries = (List<Map<String, Object>>) element.get("productServices");
			AtomicInteger serviceIndex = new AtomicInteger();
			
			serviceEntries.stream().forEach( serviceElement -> {
				
				if((boolean) serviceElement.get("isServiceSelected")) {
					
					OrderEntryModel orderServiceEntryModel = new OrderEntryModel();
					orderServiceEntryModel.setPk(supportFunctionsProvider.generateUUID());
					orderServiceEntryModel.setEntryCode((String) serviceElement.get("serviceId"));
					orderServiceEntryModel.setOrderId(orderId);
					orderServiceEntryModel.setCodeShortDescription((String) serviceElement.get("serviceName"));
					orderServiceEntryModel.setEntryId(generateServiceEntryId(index.get(), serviceIndex.incrementAndGet()));
					
					Map<String, Double> servicePriceElements = (Map<String, Double>) serviceElement.get("priceValues");
					
					if(servicePriceElements.get("offerPrice") != null) {
						
						orderServiceEntryModel.setPayablePrice(servicePriceElements.get("offerPrice"));
						orderServiceEntryModel.setBasePrice(servicePriceElements.get("basePrice"));
						orderServiceEntryModel.setLineTotal(servicePriceElements.get("offerPrice"));
					}else {
						
						orderServiceEntryModel.setPayablePrice(servicePriceElements.get("basePrice"));
						orderServiceEntryModel.setBasePrice(servicePriceElements.get("basePrice"));
						orderServiceEntryModel.setLineTotal(servicePriceElements.get("basePrice"));
					}
					
					orderEntries.add(orderServiceEntryModel);
				}
			});
			
		});
		
		List<Map<String , Object>> deliveryServiceEntry = (List<Map<String, Object>>) basket.get("basketDeliveryServices");
		
		deliveryServiceEntry.stream().forEach( deliveryElement -> {
			
			if((boolean) deliveryElement.get("isServiceSelected")) {
				
				OrderEntryModel orderServiceEntryModel = new OrderEntryModel();
				orderServiceEntryModel.setPk(supportFunctionsProvider.generateUUID());
				orderServiceEntryModel.setEntryCode((String) deliveryElement.get("serviceId"));
				orderServiceEntryModel.setOrderId(orderId);
				orderServiceEntryModel.setCodeShortDescription((String) deliveryElement.get("serviceName"));
				orderServiceEntryModel.setEntryId("DEL");
				
				Map<String, Double> deliveryPriceElements = (Map<String, Double>) deliveryElement.get("priceValues");
				
				if(deliveryPriceElements.get("offerPrice") != null) {
					
					orderServiceEntryModel.setPayablePrice(deliveryPriceElements.get("offerPrice"));
					orderServiceEntryModel.setBasePrice(deliveryPriceElements.get("basePrice"));
					orderServiceEntryModel.setLineTotal(deliveryPriceElements.get("offerPrice"));
				}else {
					
					orderServiceEntryModel.setPayablePrice(deliveryPriceElements.get("basePrice"));
					orderServiceEntryModel.setBasePrice(deliveryPriceElements.get("basePrice"));
					orderServiceEntryModel.setLineTotal(deliveryPriceElements.get("basePrice"));
				}
				
				orderEntries.add(orderServiceEntryModel);
			}
		});
		
		
		List<OrderEntryModel> response = orderEntryRepository.saveAllAndFlush(orderEntries);
		
		if(!response.isEmpty()) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public boolean updateOrderCostSummary(String guid, String orderId, Map<String, Object> basket) {
		
		Map<String , Double> cartSummary = (Map<String, Double>) basket.get("basketCostSummary");
		
		OrderCostSummaryModel orderCostSummaryModel = new OrderCostSummaryModel();
		orderCostSummaryModel.setOrderId(orderId);
		
		orderCostSummaryModel.setLineTotalWithDiscount(cartSummary.get("lineSubTotalWithDiscount"));
		orderCostSummaryModel.setLineTotalWithoutDiscount(cartSummary.get("lineSubTotalWithOutDiscount"));
		
		orderCostSummaryModel.setServiceTotalWithDiscount(cartSummary.get("serviceSubTotalWithDiscount"));
		orderCostSummaryModel.setServiceTotalWithoutDiscount(cartSummary.get("serviceSubTotalWithOutDiscount"));
		
		orderCostSummaryModel.setOrderTotalWithDiscount(cartSummary.get("orderTotalWithDiscount"));
		orderCostSummaryModel.setOrderTotalWithoutDiscount(cartSummary.get("orderTotalWithOutDiscount"));
		
		orderCostSummaryModel.setOrderSavings(cartSummary.get("cartSavings"));
		orderCostSummaryModel.setTotalQuantity(cartSummary.get("cartCount"));
		
		if(orderCostSummaryRepository.save(orderCostSummaryModel) != null) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public boolean createAddressEntries(String guid, String orderId, String emailId) {
		
		List<UserAddressModel> cartAddressList = userAddressHandler.getSelectedCartAddress(guid, emailId);
		
		if(!cartAddressList.isEmpty()) {
			
			cartAddressList.stream().forEach( addressElement -> {
				
				OrderAddressModel orderAddressModel = new OrderAddressModel();
				
				orderAddressModel.setPk(supportFunctionsProvider.generateUUID());
				orderAddressModel.setEmailId(addressElement.getEmailId());
				orderAddressModel.setOrderId(orderId);
				orderAddressModel.setLine1(addressElement.getLine1());
				orderAddressModel.setLine2(addressElement.getLine2());
				orderAddressModel.setPincode(addressElement.getPincode());
				orderAddressModel.setCountryCode(addressElement.getCountryCode());
				orderAddressModel.setCountry(addressElement.getCountry());
				orderAddressModel.setCity(addressElement.getCity());
				orderAddressModel.setState(addressElement.getState());
				orderAddressModel.setPhoneNumber(addressElement.getPhoneNumber());
				orderAddressModel.setAddressTitle(addressElement.getAddressTitle());
				orderAddressModel.setAddressType(addressElement.getAddressType());
				orderAddressModel.setBusinessType(addressElement.getBusinessType());
				orderAddressModel.setCompanyName(addressElement.getCompanyName());
				
				orderAddressRepository.saveAndFlush(orderAddressModel);
			});
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean updateCartUserAssociation(String guid) {
		int status = cartUserAssociationRepository.updateOrderSuccessStatus(guid);
		
		if(status > 0) {
			return true;
		}
		
		return false;
	}

}

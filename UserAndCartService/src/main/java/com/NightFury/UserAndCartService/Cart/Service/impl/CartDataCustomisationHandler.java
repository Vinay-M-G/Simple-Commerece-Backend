package com.NightFury.UserAndCartService.Cart.Service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.UserAndCartService.UserAndCartServiceApplication;
import com.NightFury.UserAndCartService.Cart.Entity.CartProductEntryModel;
import com.NightFury.UserAndCartService.Cart.Repository.CartDeliveryServiceEntryModelRepository;
import com.NightFury.UserAndCartService.Cart.Repository.CartProductEntryRepository;
import com.NightFury.UserAndCartService.Cart.Repository.CartProductServiceEntryRepository;
import com.NightFury.UserAndCartService.Cart.Service.dao.CartDataCustomiser;
import com.NightFury.UserAndCartService.Support.SupportFunctionsProvider;

@Service
public class CartDataCustomisationHandler implements CartDataCustomiser {
	
	public static final Logger logger = LoggerFactory.getLogger(UserAndCartServiceApplication.class);
	private static final String NO_DELIVERY_SERVICE_ADDED = "NoDeliveryServiceAdded";
	
	@Autowired
	CartProductEntryRepository cartProductEntryRepository;
	
	@Autowired
	CartProductServiceEntryRepository cartProductServiceEntryRepository;
	
	@Autowired
	CartDeliveryServiceEntryModelRepository cartDeliveryServiceEntryModelRepository;
	
	@Autowired
	SupportFunctionsProvider supportFunctionsProvider;
	
	private int getProductQuantity(String guid, String productCode) {
		List<CartProductEntryModel> productModel = cartProductEntryRepository.reteriveProductEntry(guid, productCode);
		return productModel.get(0).getQuantity();
	}
	
	private List<String> selectedServices(String guid, String productCode){
		return cartProductServiceEntryRepository.retriveServiceEntries(guid, productCode);
	}
	
	private String selectedDeliveryService(String guid){
		
		try {
			return cartDeliveryServiceEntryModelRepository.findById(guid).get().getServiceCode();
			
		}catch(NoSuchElementException e) {
			
			return NO_DELIVERY_SERVICE_ADDED;
		}
	}

	private double computeProductLineTotal(double payablePrice, int Quantity) {
		double lineTotal = payablePrice * Quantity;
		return supportFunctionsProvider.formattedCostValue(lineTotal);
	}
	
	private double computeServiceCost(double price, int Quantity, boolean multiplier) {
		
		if(multiplier) {
			double serviceCost = price * Quantity;
			return supportFunctionsProvider.formattedCostValue(serviceCost);
			
		}else {
			return price;
		}
	}
	
	@Override
	public List<Map<String, Object>> serviceMapper(JsonArray productServiceDetails, String guid, String productId, int productQuantity) {
		
		List<String> selectedServiceEntries = new ArrayList<>();
		String selectedDeliveryServiceId = null;
		
		if(productId != null) {
			selectedServiceEntries = selectedServices(guid, productId);
		}else {
			selectedDeliveryServiceId = selectedDeliveryService(guid);
		}
		
		List<Map<String, Object>> serviceEntries = new ArrayList<>();
		
		for(int index = 0; index < productServiceDetails.size(); index++) {
			JsonObject entry = productServiceDetails.getJsonObject(index);
			Map<String , Object> serviceEntry = new HashMap<>();
			Map<String , Object> servicePriceMap = new HashMap<>();
			boolean isMultiplier = entry.getBoolean("isMultiplier");
			
			serviceEntry.put("serviceId", entry.getString("serviceId"));
			serviceEntry.put("serviceName", entry.getString("serviceName"));
			serviceEntry.put("serviceDescription", entry.getString("serviceDescription"));
			serviceEntry.put("serviceType", entry.getString("serviceType"));
			serviceEntry.put("isMultiplier", isMultiplier);
			
			
			if(entry.getJsonObject("priceValues").containsKey("offerPrice")) {
				double payablePrice = entry.getJsonObject("priceValues").getJsonNumber("offerPrice").doubleValue();
				servicePriceMap.put("offerPrice", computeServiceCost(payablePrice, productQuantity, isMultiplier ));
				
				double basePrice = entry.getJsonObject("priceValues").getJsonNumber("basePrice").doubleValue();
				servicePriceMap.put("basePrice", computeServiceCost(basePrice, productQuantity, isMultiplier));
				
			}else {
				double basePrice = entry.getJsonObject("priceValues").getJsonNumber("basePrice").doubleValue();
				servicePriceMap.put("basePrice", computeServiceCost(basePrice, productQuantity, isMultiplier));
			}
			
			serviceEntry.put("priceValues", servicePriceMap);
			
			if(productId != null) {
				if (selectedServiceEntries.contains(entry.getString("serviceId"))) {
					serviceEntry.put("isServiceSelected", true);
				} else {
					serviceEntry.put("isServiceSelected", false);
				}
				
			}else {
				if (selectedDeliveryServiceId.equals(entry.getString("serviceId"))) {
					serviceEntry.put("isServiceSelected", true);
				} else {
					serviceEntry.put("isServiceSelected", false);
				}
			}
			
			serviceEntries.add(serviceEntry);
		}
		
		return serviceEntries;
	}
	
	
	@Override
	public Map<String, Object> productMapper(JsonObject productDetail, JsonArray productServiceDetails, String guid) {
		
		String productId = productDetail.getString("productId");
		Map<String , Object> productMap = new HashMap<>();
		
		try {
			Map<String , Object> priceMap = new HashMap<>();
			int productQuantity = getProductQuantity(guid, productId);
			
			productMap.put("productId", productDetail.getString("productId"));
			productMap.put("productModelId", productDetail.getString("productModelId"));
			productMap.put("productUrl", productDetail.getString("productUrl"));
			productMap.put("productDescription", productDetail.getString("productDescription"));
			productMap.put("productName", productDetail.getString("productName"));
			productMap.put("quantity", productQuantity);
			
			if(productDetail.getJsonObject("priceValues").containsKey("offerPrice")) {
				
				double payablePrice = productDetail.getJsonObject("priceValues").getJsonNumber("offerPrice").doubleValue();
				priceMap.put("offerPrice", payablePrice);
				priceMap.put("basePrice", productDetail.getJsonObject("priceValues").getJsonNumber("basePrice").doubleValue());
				
				double lineTotal = computeProductLineTotal(payablePrice, productQuantity);
				priceMap.put("lineTotal", lineTotal);
				productMap.put("priceValues", priceMap);
				
			}else {
				double payablePrice = productDetail.getJsonObject("priceValues").getJsonNumber("basePrice").doubleValue();
				priceMap.put("basePrice", payablePrice);
				
				double lineTotal = computeProductLineTotal(payablePrice, productQuantity);
				priceMap.put("lineTotal", lineTotal);
				
				productMap.put("priceValues", priceMap);
			}
			
			
			productMap.put("productServices", serviceMapper(productServiceDetails, guid, productId, productQuantity));
			
			
		}catch(Exception ex) {
			logger.error(ex.toString());
		}
		
		return productMap;
	}

	

}

package com.NightFury.UserAndCartService.Cart.Service.impl;

import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.UserAndCartService.UserAndCartServiceApplication;
import com.NightFury.UserAndCartService.Cart.Repository.CartProductEntryRepository;
import com.NightFury.UserAndCartService.Cart.Service.dao.CartDataProvider;
import com.NightFury.UserAndCartService.JsonReader.GlobalJsonReader;
import com.NightFury.UserAndCartService.Proxy.ProductDetailsProvider;

@Service
public class CartDataHandler implements CartDataProvider {
	
	public static final Logger logger = LoggerFactory.getLogger(UserAndCartServiceApplication.class);
	
	@Autowired
	CartProductEntryRepository cartProductEntryRepository;
	
	@Autowired
	ProductDetailsProvider productDetailsProvider;
	
	@Autowired
	GlobalJsonReader globalJsonReader;
	
	@Override
	public JsonObject getProductDetailForCart(String productCode) {
		try {
			
			String response = productDetailsProvider.getProduct(productCode);
			return globalJsonReader.sendJsonObjectFromString(response);
			
		}catch(Exception ex){
			logger.error("Error occured while fetching details for product: " + productCode + ex.toString());
			return null;
		}
		
	}

	@Override
	public JsonArray getServiceDetailForProduct(String productCode) {
		try {
			
			String response = productDetailsProvider.getProductServices(productCode);
			return globalJsonReader.sendJsonArrayFromString(response);
			
		}catch(Exception ex) {
			logger.error("Error occured while fetching details for product: " + productCode + ex.toString());
			return null;
		}
		
	}

	@Override
	public List<String> getProductsInCart(String guid) {
		return cartProductEntryRepository.reteriveProductEntries(guid);
	}

	@Override
	public JsonArray getDeliveryServiceForProducts(List<String> products) {
		String codes = "";
		
		try {
			
			for(String element : products) {
				codes = codes + element + ",";
			}
		
			codes = codes.substring(0, codes.length()-1);
			
			String response = productDetailsProvider.getCartDeliveryServices(codes);
			return globalJsonReader.sendJsonArrayFromString(response);
			
		}catch(Exception ex) {
			logger.error("Error occured while fetching details for product: " + codes + ex.toString());
			return null;
		}
		
	}

}

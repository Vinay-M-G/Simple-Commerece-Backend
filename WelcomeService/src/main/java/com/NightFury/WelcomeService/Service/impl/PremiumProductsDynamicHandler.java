package com.NightFury.WelcomeService.Service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.WelcomeService.JsonReader.GlobalJsonReader;
import com.NightFury.WelcomeService.Proxy.ProductServer;
import com.NightFury.WelcomeService.Service.dao.PremiumProductProvider;

@Service
public class PremiumProductsDynamicHandler implements PremiumProductProvider{
	
	@Autowired
	ProductServer productServer;
	
	@Autowired
	GlobalJsonReader globalJsonReader;

	@Override
	public String getListOfProducts() {
		return productServer.getProductDetails();
	}

	@Override
	public List<Map<String, Object>> getPremiumProducts() {
		List<Map<String, Object>> response = new ArrayList<>();
		
		String responseFromProdServer = getListOfProducts();
		
		JsonObject jso = globalJsonReader.sendJsonObjectFromString(responseFromProdServer);
		JsonArray products = jso.getJsonArray("productList");
		
		for(int index = 0 ; index < products.size() ; index++) {
			if(products.getJsonObject(index).getBoolean("isPremiumProduct")) {
				Map<String , Object> productCounter = new HashMap<String , Object>();
				Map<String , Object> priceMapper = new HashMap<String , Object>();
				
				productCounter.put("productName", products.getJsonObject(index).getString("productName"));
				productCounter.put("productModelId", products.getJsonObject(index).getString("productModelId"));
				
				if(products.getJsonObject(index).getJsonObject("priceValues").containsKey("offerPrice")) {
					priceMapper.put("basePrice", products.getJsonObject(index).getJsonObject("priceValues").getJsonNumber("basePrice").doubleValue());
					priceMapper.put("offerPrice", products.getJsonObject(index).getJsonObject("priceValues").getJsonNumber("offerPrice").doubleValue());
				}else {
					priceMapper.put("basePrice", products.getJsonObject(index).getJsonObject("priceValues").getJsonNumber("basePrice").doubleValue());
				}
				 
				productCounter.put("priceValues",priceMapper);
				productCounter.put("imageUrl", products.getJsonObject(index).getString("productUrl"));
				productCounter.put("description", products.getJsonObject(index).getString("productDescription"));
				
				response.add(productCounter);
			}
		}
		return response;
	}

}

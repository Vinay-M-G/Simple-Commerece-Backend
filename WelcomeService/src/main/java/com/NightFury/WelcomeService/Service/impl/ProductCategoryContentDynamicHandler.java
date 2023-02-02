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
import com.NightFury.WelcomeService.Service.dao.ProductCategoryProvider;

@Service
public class ProductCategoryContentDynamicHandler implements ProductCategoryProvider {
	
	@Autowired
	ProductServer productServer;
	
	@Autowired
	GlobalJsonReader globalJsonReader;

	@Override
	public String getListOfProductCategory() {
		return productServer.getProductDetails();
	}

	@Override
	public List<Map<String, Object>> sendTailoredListOfCategory() {
		List<Map<String, Object>> response = new ArrayList<>();
		Map<String , Integer> productCounter = new HashMap<String , Integer>();
		
		String responseFromProdServer = getListOfProductCategory();
		
		JsonObject jso = globalJsonReader.sendJsonObjectFromString(responseFromProdServer);
		JsonArray products = jso.getJsonArray("productList");
		
		// count the number of products with respect to their category
		for(int index = 0; index < products.size() ; index++) {
			JsonObject element = products.getJsonObject(index);
			String ctgry = element.getString("productCategory");
			
			if(productCounter.containsKey(ctgry)) {
				int count = productCounter.get(ctgry);
				count++;
				productCounter.replace(ctgry, count);
			}else {
				productCounter.put(ctgry, 1);
			}
			
		}
		
		// Map the Category and count to send as response
		for(int index = 0; index < productCounter.size() ; index++) {
			Map<String,Object> responseBodyBuilder = new HashMap<String , Object>();
			responseBodyBuilder.put("rank", index+1);
			String categoryName = (String) productCounter.keySet().toArray()[index];
			responseBodyBuilder.put("productModelCount", productCounter.get(categoryName));
			responseBodyBuilder.put("categoryName", categoryName);
			response.add(responseBodyBuilder);
		}

		return response;
	}
	
	
	
	
	
	

}

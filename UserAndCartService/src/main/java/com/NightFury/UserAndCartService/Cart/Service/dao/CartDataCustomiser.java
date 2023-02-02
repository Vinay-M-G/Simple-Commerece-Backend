package com.NightFury.UserAndCartService.Cart.Service.dao;

import java.util.List;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;

public interface CartDataCustomiser {
	
	/*
	 * @param JsonObject productDetail - parsed details of a product
	 * @param JsonArray productServiceDetails - parsed list of product services eligible for the product
	 * @param String guid - cart id 
	 * 
	 * <p> This function receives product and product service details in parsed manner
	 * and return a map with computed cost for products and services <p>
	 * 
	 * @author Vinay M G
	 * @Since Release 2.0
	 * 
	 */
	public Map<String , Object> productMapper(JsonObject productDetail, JsonArray productServiceDetails, String guid);
	
	public List<Map<String, Object>> serviceMapper(JsonArray productServiceDetails, String guid, String productId, int productQuantity);
	
}

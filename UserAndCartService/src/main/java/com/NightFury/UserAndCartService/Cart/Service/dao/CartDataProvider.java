package com.NightFury.UserAndCartService.Cart.Service.dao;

import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;

public interface CartDataProvider {
	
	public JsonObject getProductDetailForCart(String productCode);
	public JsonArray getServiceDetailForProduct(String productCode);
	public List<String> getProductsInCart(String guid);
	public JsonArray getDeliveryServiceForProducts(List<String> products);
}

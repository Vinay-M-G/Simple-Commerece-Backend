package com.NightFury.UserAndCartService.Cart.Service.dao;

import java.util.Map;

public interface CartPopulator {

	public Map<String, Object> addProduct(String productCode , String guid);
	public Map<String, Object> updateProduct(String productCode , String guid, int Quantity);
	public Map<String, Object> addService(String productCode, String guid, String serviceId);
	public Map<String, Object> removeService(String productCode, String guid, String serviceId);
	public Map<String, Object> addDeliveryService(String serviceId, String guid);
}

package com.NightFury.UserAndCartService.Order.Service.dao;

import java.util.Map;

public interface OrderProcessor {
	
	public String createOrderId(String guid, String emailId);
	
	public boolean createOrderEntries(String guid, String orderId, Map<String, Object> basket);
	
	public boolean updateOrderCostSummary(String guid, String orderId, Map<String, Object> basket);
	
	public boolean createAddressEntries(String guid, String orderId, String emailId);
	
	public boolean updateCartUserAssociation(String guid);
	
	
}

package com.NightFury.UserAndCartService.Order.Service.dao;

import java.util.List;

import com.NightFury.UserAndCartService.Order.Entity.CartOrderAssociationModel;
import com.NightFury.UserAndCartService.Order.Entity.OrderAddressModel;
import com.NightFury.UserAndCartService.Order.Entity.OrderCostSummaryModel;
import com.NightFury.UserAndCartService.Order.Entity.OrderEntryModel;

public interface OrderProvider {
	
	public List<OrderEntryModel> getOrderEntries(String orderId);
	
	public List<OrderAddressModel> getOrderAddressEntries(String orderId);
	
	public List<OrderCostSummaryModel> getOrderCostSummary(String orderId);
	
	public List<CartOrderAssociationModel> getCartOrderAssociationModel(String orderId);
	
}

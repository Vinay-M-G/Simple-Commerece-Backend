package com.NightFury.UserAndCartService.Cart.Service.dao;

import java.util.List;
import java.util.Map;

import com.NightFury.UserAndCartService.Cart.Entity.CartCostModel;

public interface CartCostCompute {
	
	public Map<String, Double> cartCostMap(CartCostModel cartCostModel);
	public CartCostModel computeCartCost(List<Map<String, Object>> itemDetails, List<Map<String, Object>> deliveryItems);
}

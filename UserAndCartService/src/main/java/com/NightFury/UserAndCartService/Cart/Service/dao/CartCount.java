package com.NightFury.UserAndCartService.Cart.Service.dao;

import java.util.List;
import java.util.Map;

public interface CartCount {
	
	public int getCartQuantity(List<Map<String , Object >> cartEntries);
}

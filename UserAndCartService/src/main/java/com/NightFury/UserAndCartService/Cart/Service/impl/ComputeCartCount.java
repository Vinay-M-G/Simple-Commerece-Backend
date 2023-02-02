package com.NightFury.UserAndCartService.Cart.Service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.NightFury.UserAndCartService.Cart.Service.dao.CartCount;

@Service
public class ComputeCartCount implements CartCount {

	@Override
	public int getCartQuantity(List<Map<String , Object >> cartEntries) {
		AtomicInteger cartCount = new AtomicInteger();
		
		cartEntries.stream().forEach(element -> {
			int quantity = (int) element.get("quantity");
			cartCount.addAndGet(quantity);
		});
		
		return cartCount.get();
	}

}

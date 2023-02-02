package com.NightFury.ProductService.Service.impl;

import org.springframework.stereotype.Service;

import com.NightFury.ProductService.Service.dao.SellableOnline;

@Service
public class SellableOnlineHandler implements SellableOnline {

	@Override
	public boolean isSellableOnline(String stockStatus, double basePrice, boolean available) {
		
		boolean response = ((stockStatus.equals("INS") || stockStatus.equals("LS")) && available && (basePrice > 0)) ? true : false;
		return response;
	}

}

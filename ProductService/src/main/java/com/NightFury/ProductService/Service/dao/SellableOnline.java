package com.NightFury.ProductService.Service.dao;

public interface SellableOnline {
	
	public boolean isSellableOnline(String stockStatus, double baseprice, boolean available);
}

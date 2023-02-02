package com.NightFury.ProductService.Service.dao;

import java.util.List;

public interface ProductFilter {
	public List<String> filterBasedOnPrice(List<String> productList, String priceRange);
	public List<String> filterBasedOnAvailability(List<String> productList);
	public List<String> stockAvailability(List<String> productList);
}

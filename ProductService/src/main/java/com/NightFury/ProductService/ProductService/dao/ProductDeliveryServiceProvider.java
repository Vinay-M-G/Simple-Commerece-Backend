package com.NightFury.ProductService.ProductService.dao;

import java.util.List;

import com.NightFury.ProductService.Entity.ProductServiceCoreModel;

public interface ProductDeliveryServiceProvider {
	public List<String> getProductDeliveryServiceIds(String productId);

	public List<String> filterEligibleDeliveryServices(List<String> productId);

	public List<ProductServiceCoreModel> getProductDeliveryServiceDetails(List<String> serviceIds);
}

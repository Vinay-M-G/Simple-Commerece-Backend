package com.NightFury.ProductService.ProductService.dao;

import java.util.List;

import com.NightFury.ProductService.Entity.ProductServiceCoreModel;

public interface ProductServiceProvider {

	public List<String> getProductServiceIds(String ProductId);

	public List<ProductServiceCoreModel> getProductServiceDetails(List<String> serviceIds);
}

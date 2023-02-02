package com.NightFury.ProductService.Service.dao;

import java.util.List;
import java.util.Map;

public interface ProductCustomiser {
	public List<Map<String,Object>> getCustomisedListofProducts(List<String> category, String sortType, List<String> filterRequested);
	public List<Map<String , Object>> singleProductDetail(String productCode);
	
}

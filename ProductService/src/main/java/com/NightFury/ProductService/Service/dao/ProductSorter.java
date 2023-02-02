package com.NightFury.ProductService.Service.dao;

import java.util.List;
import java.util.Map;

public interface ProductSorter {
	public List<Map<String, Object>> sortForPayablePrice(List<Map<String, Object>> finalPayload);
}

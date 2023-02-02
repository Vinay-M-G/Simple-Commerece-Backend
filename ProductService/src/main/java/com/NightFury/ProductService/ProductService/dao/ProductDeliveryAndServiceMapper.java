package com.NightFury.ProductService.ProductService.dao;

import java.util.List;
import java.util.Map;

import com.NightFury.ProductService.Entity.ProductServiceCoreModel;

public interface ProductDeliveryAndServiceMapper {

	public List<Map<String, Object>> createServiceMap(List<ProductServiceCoreModel> services);

	public List<Map<String, Object>> refineServiceList(List<Map<String, Object>> servicesMap);
}

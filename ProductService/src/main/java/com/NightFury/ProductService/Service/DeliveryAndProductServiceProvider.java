package com.NightFury.ProductService.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.ProductService.ProductServiceApplication;
import com.NightFury.ProductService.Entity.ProductServiceCoreModel;
import com.NightFury.ProductService.ProductService.impl.ProductDeliveryAndServiceMapHandler;
import com.NightFury.ProductService.ProductService.impl.ProductDeliveryServiceDynamicHandler;
import com.NightFury.ProductService.ProductService.impl.ProductServiceDynamicHandler;

@Service
public class DeliveryAndProductServiceProvider {
	private static Logger logger = LoggerFactory.getLogger(ProductServiceApplication.class);
	
	@Autowired
	ProductDeliveryAndServiceMapHandler productDeliveryAndServiceMapHandler;
	
	@Autowired
	ProductServiceDynamicHandler productServiceDynamicHandler;
	
	@Autowired
	ProductDeliveryServiceDynamicHandler productDeliveryServiceDynamicHandler;
	
	public List<Map<String, Object>> sendProductServices(String productCode){
		List<String> serviceIds = productServiceDynamicHandler.getProductServiceIds(productCode);
		List<ProductServiceCoreModel> servicesDetail = productServiceDynamicHandler.getProductServiceDetails(serviceIds);
		List<Map<String , Object>> serviceMap = productDeliveryAndServiceMapHandler.createServiceMap(servicesDetail);
		return serviceMap;
	}
	
	public List<Map<String,Object>> sendDeliveryServices(String products){
		
		if(products != null) {
			List<String> filteredDeliveryService = productDeliveryServiceDynamicHandler.filterEligibleDeliveryServices(Arrays.asList(products.split(",")));
			List<ProductServiceCoreModel> deliveryServiceDetails = productDeliveryServiceDynamicHandler.getProductDeliveryServiceDetails(filteredDeliveryService);
			List<Map<String , Object>> serviceMap = productDeliveryAndServiceMapHandler.createServiceMap(deliveryServiceDetails);
			return serviceMap;
		}
		
		return new ArrayList<>();
	}	
	
	
	
}

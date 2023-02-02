package com.NightFury.ProductService.ProductService.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.NightFury.ProductService.ProductServiceApplication;
import com.NightFury.ProductService.Entity.ProductServiceCoreModel;
import com.NightFury.ProductService.ProductService.dao.ProductDeliveryAndServiceMapper;

@Service
public class ProductDeliveryAndServiceMapHandler implements ProductDeliveryAndServiceMapper {
	
	private static Logger logger = LoggerFactory.getLogger(ProductServiceApplication.class);
	public static final String PRICE_VALUE = "priceValues";
	public static final String OFFER = "offerPrice";
	public static final String SERVICE_ID = "serviceId";
	public static final String BASE_PRICE = "basePrice";
	
	@Override
	public List<Map<String, Object>> createServiceMap(List<ProductServiceCoreModel> services) {
		List<Map<String, Object>> servicesMappedList = new ArrayList<>();
		
		try {
			
			services.stream().forEach( element -> {
				Map<String , Object> serviceMap = new HashMap<String ,Object>();
				Map<String , Object> priceObject = new HashMap<String ,Object>();
				
				if(element.getsPriceType().equals("Offer")) {
					priceObject.put("offerPrice", element.getsPriceValue());
					serviceMap.put("priceValues", priceObject);
				}else {
					priceObject.put("basePrice", element.getsPriceValue());
					serviceMap.put("priceValues", priceObject);
				}
				
				serviceMap.put("serviceName", element.getsServiceName());
				serviceMap.put("serviceDescription", element.getsShortDescription());
				serviceMap.put("serviceId", element.getsId());
				serviceMap.put("serviceType", element.getsServiceType());
				serviceMap.put("isMultiplier", element.isMultiplier());
				
				servicesMappedList.add(serviceMap);
			});
			
		}catch(Exception ex) {
			logger.error("Error Occured while creating Service Map");
		}
		
		return refineServiceList(servicesMappedList);
	}
	
	/*
	 * This function is used to refine services based on the price
	 * logic derived from function sortForPayablePrice
	 * @Author : Vinay M G
	 */
	@Override
	public List<Map<String, Object>> refineServiceList(List<Map<String, Object>> servicesMap) {
		
		List<Integer> duplicateIndexes = new ArrayList<Integer>();
		
		try {
			
			servicesMap.stream().forEach( element -> {
				if(element != null) {
					Map<String, Object> priceObj = (Map<String, Object>) element.get(PRICE_VALUE);
					
					if(priceObj.containsKey(OFFER)) {
						String serviceId = (String) element.get(SERVICE_ID);
						AtomicInteger index = new AtomicInteger();
						
						servicesMap.stream().forEach(subElement -> {
							double serviceBasePrice = 0.0;
							Map<String, Object> priceObjInSubEle = (Map<String, Object>) subElement.get(PRICE_VALUE);
							String serviceIdInSubEle = (String) subElement.get(SERVICE_ID);
							
							if(serviceIdInSubEle.equals(serviceId) && priceObjInSubEle.containsKey(BASE_PRICE)) {
								serviceBasePrice = (double) priceObjInSubEle.get(BASE_PRICE);
								priceObj.put(BASE_PRICE, serviceBasePrice);
								element.replace(PRICE_VALUE, priceObj);
								duplicateIndexes.add(index.get()); 
								
							}else {
								index.getAndIncrement();
							}
						});
						
					}
				}
			});
			
			if(duplicateIndexes != null) {
				for(int dupIndex : duplicateIndexes ) {
					servicesMap.get(dupIndex).clear();
				}
				logger.info("Removed Duplicate Objects");
				logger.info("indexes of duplicate products in list "+ duplicateIndexes.toString());
			}
			
		}catch(IndexOutOfBoundsException iex) {
			
			logger.warn("A Service has more than two prices");
			iex.printStackTrace();
		}catch(Exception ex) {
			
			ex.printStackTrace();
		}
		
		return servicesMap.stream().filter(element -> !element.isEmpty()).toList();
	}

}

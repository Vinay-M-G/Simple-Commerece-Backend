package com.NightFury.ProductService.Service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.ProductService.ProductServiceApplication;
import com.NightFury.ProductService.Entity.ProductConfigurationModel;
import com.NightFury.ProductService.Repository.ProductConfigurationRepository;
import com.NightFury.ProductService.Service.dao.ProductConfiguration;

@Service
public class ProductConfigurationHandler implements ProductConfiguration {
	
	public static final Logger logger = LoggerFactory.getLogger(ProductServiceApplication.class);
	
	public static final String CATEGORY = "CATEGORY";
	public static final String FILTER = "FILTER";
	public static final String SORT = "SORT";
	
	@Autowired
	ProductConfigurationRepository productConfigurationRepository;
	
	@Override
	public Map<String, Object> getProductConfigurations() {
		Map<String , Object > response = new HashMap<String , Object>();
		List<Map<String , Object>> category = new ArrayList<>();
		List<Map<String , Object>> filter = new ArrayList<>();
		List<Map<String , Object>> sort = new ArrayList<>();
		
		try {
			List<ProductConfigurationModel> configs = productConfigurationRepository.findAll();
			
			configs.stream().forEach( element -> {
				
				if(element.getParam().equals(CATEGORY)) {
					Map<String , Object> tempVal = new HashMap<String , Object>();
					tempVal.put("value", element.isPcValue());
					tempVal.put("key", element.getPcKey());
					category.add(tempVal);
				}
				
				if(element.getParam().equals(FILTER)) {
					Map<String , Object> tempVal = new HashMap<String , Object>();
					tempVal.put("value", element.isPcValue());
					tempVal.put("key", element.getPcKey());
					filter.add(tempVal);
				}
				
				if(element.getParam().equals(SORT)) {
					Map<String , Object> tempVal = new HashMap<String , Object>();
					tempVal.put("value", element.isPcValue());
					tempVal.put("key", element.getPcKey());
					sort.add(tempVal);
				}
				
			});
			
			response.put("sortTypes", sort);
			response.put("filters", filter);
			response.put("categories", category);
			
		}catch(Exception ex) {
			logger.error("Exception occured while parsing data : " + ex.toString());
		}
		
		return response;
	}

}

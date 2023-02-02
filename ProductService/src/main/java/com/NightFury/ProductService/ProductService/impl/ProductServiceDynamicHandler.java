package com.NightFury.ProductService.ProductService.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.ProductService.ProductServiceApplication;
import com.NightFury.ProductService.Entity.ProductServiceCoreModel;
import com.NightFury.ProductService.ProductService.dao.ProductServiceProvider;
import com.NightFury.ProductService.Repository.ProductServiceCoreModelRepository;
import com.NightFury.ProductService.Repository.ProductServiceMappingModelRepository;

@Service
public class ProductServiceDynamicHandler implements ProductServiceProvider {
	
	private static Logger logger = LoggerFactory.getLogger(ProductServiceApplication.class);
	
	@Autowired
	ProductServiceMappingModelRepository productServiceMappingModelRepository;
	
	@Autowired
	ProductServiceCoreModelRepository productServiceCoreModelRepository;
	
	@Override
	public List<String> getProductServiceIds(String ProductId) {
		List<String> productServiceIds = new ArrayList<String>();
		try {
			productServiceIds = productServiceMappingModelRepository.retriveServiceIds(ProductId);
			logger.info(productServiceIds.toString());
			if(productServiceIds.isEmpty()) {
				logger.info(ProductId + "has no Product services");
			}
			
		}catch(Exception ex) {
			logger.error("Error occured while retriving product service details");
		}

		return productServiceIds;
	}

	@Override
	public List<ProductServiceCoreModel> getProductServiceDetails(List<String> serviceIds) {
		
		List<ProductServiceCoreModel> serviceDetails = new ArrayList<ProductServiceCoreModel>();
		try {
			
			if(!serviceIds.isEmpty()) {
				serviceDetails = productServiceCoreModelRepository.retriveServiceDetails(serviceIds);
			}
			
		}catch(Exception ex) {
			logger.error("Error occured while retriving product service details");
		}
		
		return serviceDetails;
	}

}

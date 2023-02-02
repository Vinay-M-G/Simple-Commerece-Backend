package com.NightFury.ProductService.ProductService.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.NightFury.ProductService.ProductServiceApplication;
import com.NightFury.ProductService.Entity.ProductServiceCoreModel;
import com.NightFury.ProductService.ProductService.dao.ProductDeliveryServiceProvider;
import com.NightFury.ProductService.Repository.ProductServiceMappingModelRepository;
import com.NightFury.ProductService.Repository.ProductServiceCoreModelRepository;

@Service
public class ProductDeliveryServiceDynamicHandler implements ProductDeliveryServiceProvider {
	
	private static Logger logger = LoggerFactory.getLogger(ProductServiceApplication.class);
	
	@Value("${deliveryService.defaultValue}")
	private String defaultDeliveryServiceId;
	
	@Autowired
	ProductServiceMappingModelRepository productServiceMappingModelRepository;
	
	@Autowired
	ProductServiceCoreModelRepository productServiceCoreModelRepository;
	
	@Override
	public List<String> getProductDeliveryServiceIds(String productId) {
		
		List<String> deliveryServiceIds = new ArrayList<>();
		
		try {
			
			deliveryServiceIds = productServiceMappingModelRepository.retriveDeliveryServiceIds(productId);
		}catch(Exception ex) {
			
			logger.warn("Error occured while reteriving delivery service list for product code :" + productId + ex.getMessage());
		}
		
		return deliveryServiceIds;
	}

	@Override
	public List<String> filterEligibleDeliveryServices(List<String> productId) {
		
		List<String> filteredServiceIds = new ArrayList<>();
		
		try {
			
			String deliveryServices[][] = new String[productId.size()][];
			int elementHighestLengthIndex = 0;
			int elementHighestLength = 0;
			
			for(int index = 0; index < productId.size() ; index++) {
				String servicesId[] = getProductDeliveryServiceIds(productId.get(index)).toArray(String[] ::new);
				deliveryServices[index] = servicesId;
				
				if(deliveryServices[index].length > elementHighestLength) {
					elementHighestLengthIndex = index;
					elementHighestLength = deliveryServices[index].length;
				}

			}
			
//			for(int i = 0; i < deliveryServices.length; i++) {
//				for(int j = 0; j < deliveryServices[i].length; j++) {
//					System.out.println(deliveryServices[i][j]);
//				}
//				System.out.println(" ");
//			}
			
			//System.out.println(elementHighestLengthIndex);
			
			String[] masterArray = deliveryServices[elementHighestLengthIndex];
			
			for(int index = 0 ; index < masterArray.length ; index++) {
				
				String serviceId = masterArray[index];
				
				for(int i = 0; i <= deliveryServices.length ; i++) {
					boolean flag = false;
					
					if(i == deliveryServices.length) {
						filteredServiceIds.add(serviceId);
						break;
					}
					
					for(int j = 0; j <= deliveryServices[i].length; j++) {
						
						if( j == deliveryServices[i].length ) {
							flag = true;
							break;
						}
						
						if(deliveryServices[i][j].equals(serviceId)) {
							
							break;
						}
					}
					
					if(flag) {
						break;
					}  
				}
			}
			 
			logger.info("Identified sevices : " + filteredServiceIds.toString());
			
			// To Send default service if no delivery service is available
			if(filteredServiceIds.isEmpty()) {
				filteredServiceIds.add(defaultDeliveryServiceId);
			}

		}catch(IndexOutOfBoundsException e) {
			
			logger.warn(e.toString());
		}catch(Exception e) {
			
			logger.warn(e.toString());
		}
		
		return filteredServiceIds;
	}

	@Override
	public List<ProductServiceCoreModel> getProductDeliveryServiceDetails(List<String> serviceIds) {
		List<ProductServiceCoreModel> deliveryServiceDetails = new ArrayList<>();
		
		try {
			
			deliveryServiceDetails = productServiceCoreModelRepository.retriveDeliveryServiceDetails(serviceIds);
		}catch(Exception ex) {
			
			logger.warn("Error occured while reteriving delivery service list for service code :" + serviceIds + ex.getMessage());
		}
		
		return deliveryServiceDetails;
	}

}

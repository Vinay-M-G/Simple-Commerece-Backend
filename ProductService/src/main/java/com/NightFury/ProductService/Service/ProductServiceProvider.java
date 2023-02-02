package com.NightFury.ProductService.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.ProductService.ProductServiceApplication;
import com.NightFury.ProductService.Service.impl.ProductConfigurationHandler;
//import com.NightFury.ProductService.GlobalDataHandler.GlobalDataParser;
import com.NightFury.ProductService.Service.impl.ProductDetailsDynamicHandler;
import com.NightFury.ProductService.Service.impl.ProductDynamicCustomiser;

@Service
public class ProductServiceProvider {
	
	private static Logger logger = LoggerFactory.getLogger(ProductServiceApplication.class);
	
	@Autowired
	ProductDetailsDynamicHandler productDetailsDynamicHandler;
	
	@Autowired
	ProductConfigurationHandler productConfigurationHandler;
	
//	@Autowired
//	GlobalDataParser globalDataParser;  
	
	@Autowired
	ProductDynamicCustomiser productDynamicCustomiser;
	
	public Map<String,Object> sendAllProducts() {
		Map<String , Object> response = productDetailsDynamicHandler.getAllProductDetails();
		return response;		
	}
	
	/*
	 * @param - product categories with comma separated
	 * @param - sort type , default lowest payable price 
	 * @param - filter values with comma separated
	 * 
	 * @return - final list of products 
	 */
	public List<Map<String,Object>> sendCustomisedProducts(String category, String sortType, String filterValue){
		List<Map<String,Object>> reponseHandler = new ArrayList<>();
		try {
			List<String> filterList = new ArrayList<>();
			List<String> categoryValueForQuery = new ArrayList<>();
			if(category != null) {
				categoryValueForQuery = Arrays.asList(category.split(","));
				logger.info("requested categories : " + categoryValueForQuery );
			}else {
				logger.warn("Category cannot be null");
			}
			
			if(filterValue != null) {
				filterList = Arrays.asList(filterValue.split(","));
				logger.info("Requested filters : " + filterList.toString());
			}
			
			logger.info("Requested Sort Type : " + sortType);
			
			reponseHandler = productDynamicCustomiser.getCustomisedListofProducts(categoryValueForQuery, sortType, filterList);
		}catch(Exception e) {
			logger.error("Error while parsing the values sent by client." + e.toString());
		}
		return reponseHandler;		
	}
	
	public Map<String , Object> keyConfiguration(){
		return productConfigurationHandler.getProductConfigurations();	
	}
	
	public Map<String , Object> sendSingleProductDetail(String productCode){
		List<Map<String , Object>> details = productDynamicCustomiser.singleProductDetail(productCode);
		return details.get(0);
	}
}

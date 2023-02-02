package com.NightFury.ProductService.Service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.ProductService.ProductServiceApplication;
import com.NightFury.ProductService.Repository.PriceModelRepository;
import com.NightFury.ProductService.Repository.ProductAvailabilityModelRepository;
import com.NightFury.ProductService.Repository.ProductStockModelRepository;
import com.NightFury.ProductService.Service.dao.ProductFilter;

/*
 * This class has all the filter functions so that the required functions can be called to 
 * cater filter needs
 * 
 * @Author : Vinay M G
 */

@Service
public class ProductDynamicFilter implements ProductFilter {
	
	public static final String PRICE_SPLIT_ATTR = "to";
	
	private static Logger logger = LoggerFactory.getLogger(ProductServiceApplication.class);
	
	@Autowired
	ProductAvailabilityModelRepository productAvailabilityModelRepository;
	
	@Autowired
	ProductStockModelRepository productStockModelRepository;
	
	@Autowired
	PriceModelRepository priceModelRepository;
	
	@Override
	public List<String> filterBasedOnPrice(List<String> productList , String priceRange) {
		HashSet<String> uniqueProducts = new HashSet<String>();
		try {
			String[] prices = priceRange.split(PRICE_SPLIT_ATTR);
			double lowestPrice = Double.parseDouble(prices[0].trim());
			double HighestPrice = Double.parseDouble(prices[1].trim());
			uniqueProducts= priceModelRepository.filterProductListBasedOnPriceRange(productList, lowestPrice, HighestPrice);
			logger.info("price filtered");
		}catch(Exception ex){
			logger.error("Exception occured while parsing data. Error : " + ex.toString());
		}
		return new ArrayList(uniqueProducts);
	}

	@Override
	public List<String> filterBasedOnAvailability(List<String> productList) {
		return productAvailabilityModelRepository.filterAvailableProducts(productList);
	}

	@Override
	public List<String> stockAvailability(List<String> productList) {
		return productStockModelRepository.filterBasedOnStock(productList);
	}

}

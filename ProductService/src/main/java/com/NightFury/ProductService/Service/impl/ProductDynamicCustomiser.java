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
import com.NightFury.ProductService.Entity.ProductCoreModel;
import com.NightFury.ProductService.GlobalDataHandler.GlobalDataParser;
import com.NightFury.ProductService.Repository.ProductCoreModelRepository;
import com.NightFury.ProductService.Repository.ProductModelRepository;
import com.NightFury.ProductService.Service.dao.ProductCustomiser;

@Service
public class ProductDynamicCustomiser implements ProductCustomiser {
	
	public static final Logger logger = LoggerFactory.getLogger(ProductServiceApplication.class);
	
	public static final String HIGHESTPRICE = "highestprice";
	public static final String LOWESTPRICE = "lowestprice";
	public static final String STOCKSTATUS = "stockstatus";
	
	public static final String FILTER_AVALIBILITY = "availability";
	public static final String FILTER_STOCK = "stock";
	public static final String FILTER_PRICERANGE = "price";
	
	
	
	@Autowired
	ProductModelRepository productModelRepository;
	
	@Autowired
	GlobalDataParser globalDataParser;
	
	@Autowired
	ProductCoreModelRepository productCoreModelRepository;
	
	@Autowired
	ProductDynamicSorter productDynamicSorter;
	
	@Autowired
	ProductDynamicFilter productDynamicFilter;
	
	@Autowired
	SellableOnlineHandler sellableOnlineHandler;
	
	/*
	 * Fuction to Create Map of Product
	 * 
	 * @Author : Vinay M G
	 * @Param : List<ProductCoreModel>
	 *
	 */
	public List<Map<String , Object>> preparePayloadForProduct(List<ProductCoreModel> rawSortedListed){
		List<Map<String, Object>> finalList = new ArrayList<>();
		
		rawSortedListed.stream().forEach(element -> {
			Map<String , Object> product = new HashMap<String , Object>();
			Map<String , Object> price = new HashMap<String , Object>();
			Map<String , Object> stock = new HashMap<String , Object>();
			Map<String , Object> availability = new HashMap<String , Object>();
			product.put("productName", element.getpShortDescription());
			product.put("productModelId", element.getpModelId());
			product.put("productDescription", element.getpLongDescription());
			product.put("productCategory", element.getpCategory());
			product.put("productId", element.getpId());
			product.put("isPremiumProduct", element.ispPremiumProduct());
			product.put("productUrl", element.getpImageUrl());
			
			if(element.getpPriceType().equalsIgnoreCase("Offer")) {
				price.put("offerPrice", element.getpPriceValue());
				product.put("priceValues", price);
			}else {
				price.put("basePrice", element.getpPriceValue());
				product.put("priceValues", price);
			}
			
			stock.put("quantity", element.getpQuantity());
			stock.put("stockStatus", element.getpStockStatus());
			product.put("stockData", stock);
			
			availability.put("availableForSale", element.isSellableOnline());
			availability.put("isSellableOnline", sellableOnlineHandler.isSellableOnline(element.getpStockStatus(), element.getpPriceValue(), element.isSellableOnline()));
			
			product.put("availabiltyOptions", availability);
			
			finalList.add(product);
		});
		
		return finalList;
	}
	
	@Override
	public List<Map<String, Object>> getCustomisedListofProducts(List<String> category, String sortType, 
			List<String> filterRequested) {
		List<Map<String, Object>> finalList = new ArrayList<>();
		List<Map<String, Object>> refinedList = new ArrayList<>();
		List<ProductCoreModel> sortedProductList = new ArrayList<>();

		// get list of products based on category
		List<String> productCustomList = productModelRepository.getProductsBasedOnCategory(category);
		
		//filter section
		if(!filterRequested.isEmpty()) {
			for(String element : filterRequested) {
				if(element.contains(FILTER_AVALIBILITY)) {
					logger.info("filtering list based on Availability");
					productCustomList = productDynamicFilter.filterBasedOnAvailability(productCustomList);
				}
				
				if(element.contains(FILTER_STOCK)) {
					logger.info("filtering list based on stock status");
					productCustomList = productDynamicFilter.stockAvailability(productCustomList);
				}
				
				if(element.contains(FILTER_PRICERANGE)) {
					try {
						String priceRange = element.split("=")[1];
						logger.info(priceRange);
						productCustomList = productDynamicFilter.filterBasedOnPrice(productCustomList, priceRange);
					}catch(Exception e) {
						logger.error("An Error occured while parsing data for price range. Error : " + e.toString());
					}
					
				}
			}
			
		}

		// sort section
			switch(sortType) {
			case HIGHESTPRICE:
				try {
					logger.info("fetching data based on highest price");
					sortedProductList = productCoreModelRepository.getProductDetailsSortedByHighestPrice(productCustomList);
					finalList = preparePayloadForProduct(sortedProductList); 
					refinedList = productDynamicSorter.sortForPayablePrice(finalList);
				}catch(Exception e) {
					logger.error("Error occured while sorting for Highest Price : Exception is " + e.toString());
				}
				break;
			
			case LOWESTPRICE:
				try {
					logger.info("fetching data based on lowest price");
					sortedProductList = productCoreModelRepository.getProductDetailsSortedByLowestPrice(productCustomList);
					finalList = preparePayloadForProduct(sortedProductList);
					refinedList = productDynamicSorter.sortForPayablePrice(finalList);
				}catch(Exception e) {
					logger.error("Error occured while sorting Lowest Price : Exception is " + e.toString());
				}
				
				break;
				
			case STOCKSTATUS:
				try {
					logger.info("fetching data based on stock status");
					sortedProductList = productCoreModelRepository.getProductDetailsSortedByStock(productCustomList);
					finalList = preparePayloadForProduct(sortedProductList);
					refinedList = productDynamicSorter.sortForPayablePrice(finalList);
				}catch(Exception e) {
					logger.error("Error occured while sorting based on Stock : Exception is " + e.toString());
				}
				break;
				
			default:
				try {
					logger.info("fetching data based on default set");
					sortedProductList = productCoreModelRepository.getProductDetailsSortedByLowestPrice(productCustomList);
					logger.warn("No Proper Attribute for Sorting was sent. Hence by default Sort is taken as Lowest price");
					finalList = preparePayloadForProduct(sortedProductList);
					refinedList = productDynamicSorter.sortForPayablePrice(finalList);
					
				}catch(Exception e) {
					logger.error("Error occured while sorting for default section : Exception is " + e.toString());
				}
			}
			
		return refinedList;

	}

	@Override
	public List<Map<String, Object>> singleProductDetail(String productCode) {
		List<ProductCoreModel> productList = productCoreModelRepository.reteriveSingleProductDetail(productCode);
		List<Map<String, Object>> finalProductList = preparePayloadForProduct(productList);
		return productDynamicSorter.sortForPayablePrice(finalProductList);
	}

}

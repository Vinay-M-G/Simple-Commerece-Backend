package com.NightFury.ProductService.Service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.NightFury.ProductService.ProductServiceApplication;
import com.NightFury.ProductService.Service.dao.ProductSorter;

/*
 * This class prepares refined payload by removing the duplicates
 * @ Author : Vinay M G
 */

@Service
public class ProductDynamicSorter implements ProductSorter {
	public static final String OFFER = "offerPrice";
	public static final String BASE = "Base";
	public static final String PRODUCT_ID = "productId";
	public static final String PRICE_VALUE = "priceValues";
	public static final String BASE_PRICE = "basePrice";
	
	public static final Logger logger = LoggerFactory.getLogger(ProductServiceApplication.class);

	@Override
	public List<Map<String, Object>> sortForPayablePrice(List<Map<String, Object>> finalPayload) {
		List<Map<String, Object>> refinedList = new ArrayList<>();
		
		try {
			List<Integer> duplicateIndexes = new ArrayList<>();
			
			finalPayload.stream().forEach(element -> {
				if (element != null) {

					Map<String, Object> priceObj = (Map<String, Object>) element.get(PRICE_VALUE);

					if (priceObj.containsKey(OFFER)) {
						String pnc = (String) element.get(PRODUCT_ID);
						double offerPrice = (double) priceObj.get(OFFER);
						AtomicInteger index = new AtomicInteger();
						
						// get index of the prodcut combined table where baseprice lies and append it to index list
						// update priceValues Object with both base and offer price
						finalPayload.stream().forEach(subElement -> {

							double basePrice = 0.0;
							Map<String, Object> priceObjInSubEle = (Map<String, Object>) subElement.get(PRICE_VALUE);
							String pncInSubEle = (String) subElement.get(PRODUCT_ID);

							if (pncInSubEle.equals(pnc) && priceObjInSubEle.containsKey(BASE_PRICE) && priceObjInSubEle.size() == 1) {
								basePrice = (double) priceObjInSubEle.get(BASE_PRICE);
								logger.info("found base price : " + basePrice + "for PNC : " + pnc);
								priceObj.put(BASE_PRICE, basePrice);
								element.replace(PRICE_VALUE, priceObj);
								duplicateIndexes.add(index.get());
								
							} else {
								index.getAndIncrement();
							}

						});

					}
				}

			});
			
			// marking all the duplicate values with empty Map
			if(duplicateIndexes != null) {
				for(int dupIndex : duplicateIndexes ) {
					finalPayload.get(dupIndex).clear();
				}
				logger.info("Removed Duplicate Objects");
				logger.info("indexes of duplicate products in list "+ duplicateIndexes.toString());
			}
			
			
		}catch(IndexOutOfBoundsException e) {
			logger.warn("Product cannot have two offer prices or two base prices");
			
		}catch(Exception ex) {
			logger.error("Error occured while removing duplicate object : Error" + ex);
			
		}
		
		// removing all the empty Map 
		refinedList = finalPayload.stream().filter(element -> !element.isEmpty()).toList();
		logger.info("No of final products : " + refinedList.size());
		
		return refinedList;
	}

}

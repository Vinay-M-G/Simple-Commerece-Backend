package com.NightFury.ProductService.Service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.ProductService.Entity.PriceModel;
import com.NightFury.ProductService.Entity.ProductModel;
import com.NightFury.ProductService.Repository.PriceModelRepository;
import com.NightFury.ProductService.Repository.ProductModelRepository;
import com.NightFury.ProductService.Service.dao.ProductDetailsProvider;

@Service
public class ProductDetailsDynamicHandler implements ProductDetailsProvider{
	
	public static final String OFFER_PRICE_TYPE = "Offer";
	public static final String BASE_PRICE_TYPE = "Base";
	
	@Autowired
	PriceModelRepository priceModelRepository;
	
	@Autowired
	ProductModelRepository productModelRepository;
	
	@Override
	public Map<String, Object> getAllProductDetails() {
		Map<String , Object> response = new HashMap<String , Object>();
		List<Map<String,Object>> responseDataofProducts = new ArrayList<>();
		List<ProductModel> responseDataFromDB = productModelRepository.findAll(); 
		List<PriceModel> responseDataForPriceFromDB = priceModelRepository.findAll();
		
		
			responseDataFromDB.stream().forEach(element ->{
			Map<String , Object> singleProductData = new HashMap<String , Object>();
			Map<String , Double> priceDataOfSingleProduct = new HashMap<String , Double>();
			
			singleProductData.put("productId", element.getpId());
			singleProductData.put("productName", element.getpShortDescription());
			singleProductData.put("productModelId",element.getpModelId());
			singleProductData.put("productDescription", element.getpLongDescription());
			singleProductData.put("productCategory", element.getpCategory());
			singleProductData.put("productUrl" , element.getpImageUrl());
			singleProductData.put("isPremiumProduct", element.ispPremiumProduct());
			
			String currentProduct = element.getpId();
			
			responseDataForPriceFromDB.stream().filter(priceElement ->
				priceElement.getpId().contains(currentProduct)
			).forEach(priceRow -> {
				if(priceRow.getpPriceType().equalsIgnoreCase(OFFER_PRICE_TYPE) && priceRow.getpPriceType() != null) {
					priceDataOfSingleProduct.put("offerPrice", Double.parseDouble(priceRow.getpPriceValue()));
				}else if(priceRow.getpPriceType() != null) {
					priceDataOfSingleProduct.put("basePrice", Double.parseDouble(priceRow.getpPriceValue()));
				}
			});
			singleProductData.put("priceValues", priceDataOfSingleProduct);
			responseDataofProducts.add(singleProductData);
		});
		
		response.put("productList", responseDataofProducts);
		return response;
		
	}


}

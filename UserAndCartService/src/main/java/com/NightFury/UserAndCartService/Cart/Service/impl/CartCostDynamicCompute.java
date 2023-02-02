package com.NightFury.UserAndCartService.Cart.Service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.UserAndCartService.UserAndCartServiceApplication;
import com.NightFury.UserAndCartService.Cart.Entity.CartCostModel;
import com.NightFury.UserAndCartService.Cart.Service.dao.CartCostCompute;
import com.NightFury.UserAndCartService.Support.SupportFunctionsProvider;

@Service
public class CartCostDynamicCompute implements CartCostCompute {
	
	public static final Logger logger = LoggerFactory.getLogger(UserAndCartServiceApplication.class);
	public static final String BASE_PRICE = "basePrice";
	public static final String OFFER_PRICE = "offerPrice";
	public static final String PRICE_COMPONENT = "priceValues";
	
	@Autowired
	SupportFunctionsProvider supportFunctionsProvider;
	
	@Autowired
	ComputeCartCount computeCartCount;
	
	@Override
	public Map<String, Double> cartCostMap(CartCostModel cartCostModel) {
		Map<String , Double> cartPriceResponse = new HashMap<>();
		
		cartPriceResponse.put("lineSubTotalWithOutDiscount", supportFunctionsProvider.formattedCostValue(cartCostModel.getLineSubTotalWithoutDiscount()));
		cartPriceResponse.put("lineSubTotalWithDiscount", supportFunctionsProvider.formattedCostValue(cartCostModel.getLineSubTotalWithDiscount()));
		
		cartPriceResponse.put("orderTotalWithOutDiscount", supportFunctionsProvider.formattedCostValue(cartCostModel.getOrderTotalWithoutDiscount()));
		cartPriceResponse.put("orderTotalWithDiscount", supportFunctionsProvider.formattedCostValue(cartCostModel.getOrderTotalWithDiscount()));
		
		cartPriceResponse.put("serviceSubTotalWithOutDiscount", supportFunctionsProvider.formattedCostValue(cartCostModel.getServiceSubTotalWithoutDiscount()));
		cartPriceResponse.put("serviceSubTotalWithDiscount", supportFunctionsProvider.formattedCostValue(cartCostModel.getServiceSubTotalWithDiscount()));
		
		cartPriceResponse.put("cartSavings", supportFunctionsProvider.formattedCostValue(cartCostModel.getCartSavings()));
		cartPriceResponse.put("cartCount", supportFunctionsProvider.formattedCostValue((double) cartCostModel.getCartProductCount()));
		
		return cartPriceResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CartCostModel computeCartCost(List<Map<String, Object>> itemDetails, List<Map<String, Object>> deliveryItems) {
		
		CartCostModel cartCostModel = new CartCostModel();
		int cartItemCount = computeCartCount.getCartQuantity(itemDetails);
		
		try {
			
			itemDetails.stream().forEach( element -> {
				
				Map<String , Double> productPrice = (Map<String, Double>) element.get(PRICE_COMPONENT);
				double payablePrice = productPrice.get("lineTotal");
				double basePayablePrice = productPrice.get(BASE_PRICE) * cartItemCount;
				
				cartCostModel.setLineSubTotalWithoutDiscount(cartCostModel.getLineSubTotalWithoutDiscount() + basePayablePrice);
				cartCostModel.setLineSubTotalWithDiscount(cartCostModel.getLineSubTotalWithDiscount() + payablePrice);
				
				List<Map<String, Object>> servicePriceList = (List<Map<String, Object>>) element.get("productServices");
				
				servicePriceList.stream().forEach( serviceElement -> {
					
					if((boolean)serviceElement.get("isServiceSelected")) {
						
						Map<String , Double> servicePrice = (Map<String, Double>) serviceElement.get(PRICE_COMPONENT);
						
						if(servicePrice.size() > 1) {
							double servicePayablePrice = servicePrice.get(OFFER_PRICE);
							cartCostModel.setServiceSubTotalWithDiscount(cartCostModel.getServiceSubTotalWithDiscount() + servicePayablePrice);
							
							double serviceBasePrice = servicePrice.get(BASE_PRICE);
							cartCostModel.setServiceSubTotalWithoutDiscount(cartCostModel.getServiceSubTotalWithoutDiscount() + serviceBasePrice);
						}else {
							
							double serviceBasePrice = servicePrice.get(BASE_PRICE);
							
							cartCostModel.setServiceSubTotalWithDiscount(cartCostModel.getServiceSubTotalWithDiscount() + serviceBasePrice);
							cartCostModel.setServiceSubTotalWithoutDiscount(cartCostModel.getServiceSubTotalWithoutDiscount() + serviceBasePrice);
						}
					}
					
				});
				
				
			});
			
			deliveryItems.stream().forEach( serviceElement -> {
				
				if((boolean)serviceElement.get("isServiceSelected")) {
					
					Map<String , Double> servicePrice = (Map<String, Double>) serviceElement.get(PRICE_COMPONENT);
					
					if(servicePrice.size() > 1) {
						double servicePayablePrice = servicePrice.get(OFFER_PRICE);
						cartCostModel.setServiceSubTotalWithDiscount(cartCostModel.getServiceSubTotalWithDiscount() + servicePayablePrice);
						
						double serviceBasePrice = servicePrice.get(BASE_PRICE);
						cartCostModel.setServiceSubTotalWithoutDiscount(cartCostModel.getServiceSubTotalWithoutDiscount() + serviceBasePrice);
					}else {
						
						double serviceBasePrice = servicePrice.get(BASE_PRICE);
						
						cartCostModel.setServiceSubTotalWithDiscount(cartCostModel.getServiceSubTotalWithDiscount() + serviceBasePrice);
						cartCostModel.setServiceSubTotalWithoutDiscount(cartCostModel.getServiceSubTotalWithoutDiscount() + serviceBasePrice);
					}
				}
				
			});
			
			double orderBaseTotal = cartCostModel.getLineSubTotalWithoutDiscount() + cartCostModel.getServiceSubTotalWithoutDiscount();
			double orderPayablePrice = cartCostModel.getLineSubTotalWithDiscount() + cartCostModel.getServiceSubTotalWithDiscount();
			
			double cartSaving = orderBaseTotal - orderPayablePrice;
			
			cartCostModel.setOrderTotalWithDiscount(orderPayablePrice);
			cartCostModel.setOrderTotalWithoutDiscount(orderBaseTotal);
			
			cartCostModel.setCartSavings(cartSaving);
			cartCostModel.setCartProductCount(cartItemCount);
			
		}catch(ClassCastException ex) {
			
			logger.error("Error occured while parsing" + ex.toString());
		}catch(Exception e) {
			
			logger.error(e.toString());
		}
		
		return cartCostModel;
	}

}

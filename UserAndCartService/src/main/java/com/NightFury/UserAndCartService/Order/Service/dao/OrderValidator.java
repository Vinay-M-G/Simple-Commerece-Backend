package com.NightFury.UserAndCartService.Order.Service.dao;

import java.util.List;
import java.util.Map;

import javax.json.JsonObject;

import com.NightFury.UserAndCartService.Cart.Entity.CartProductEntryModel;

public interface OrderValidator {
	
	/*
	 * Returns false if user has added more quantity than the available quantity
	 * 
	 * @author : Vinay M G
	 * @param : product -> unique product number
	 * @param : cartProducts -> list of products the user has selected
	 * @param : availableQuantity -> Available Quantity of the respective product
	 */
	public boolean validateCartForAvailableQuantity(String product, List<CartProductEntryModel> cartProducts, int availableQuantity);
	
	public boolean validateCartForPriceChange(double payablePrice, Map<String, Object> basket);
	
	public boolean isACart(String guid);
	
	public boolean isOrderExist(String guid);
}

package com.NightFury.UserAndCartService.Cart.Service.dao;

public interface CartCreator {
	
	public String getExistingCartId(String emailId);
	public String createNewCartId(String emailId);
}

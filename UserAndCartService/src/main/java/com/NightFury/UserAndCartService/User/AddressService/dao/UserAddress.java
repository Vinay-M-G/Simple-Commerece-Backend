package com.NightFury.UserAndCartService.User.AddressService.dao;

import java.util.List;
import java.util.Map;

import javax.json.JsonObject;

import com.NightFury.UserAndCartService.User.Entity.UserAddressModel;

public interface UserAddress {
	
	public List<UserAddressModel> getUserAddressList(String emailId, String businessType);
	
	public boolean updateUserAddress(JsonObject requestBody, String emailId);
	
	public boolean updateUserAddressForCart(String guid, JsonObject requestBody);
}

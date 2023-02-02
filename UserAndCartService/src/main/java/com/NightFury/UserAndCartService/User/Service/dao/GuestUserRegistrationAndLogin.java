package com.NightFury.UserAndCartService.User.Service.dao;

import java.util.Map;

import javax.json.JsonObject;

import com.NightFury.UserAndCartService.User.ExceptionsHandler.RegisteredUserAlreadyExist;

public interface GuestUserRegistrationAndLogin {
	public boolean isExistingUser(String User);
	public Map<String, Object> createGuestUserAction(JsonObject request) throws RegisteredUserAlreadyExist;
	public boolean isRegisteredUser(String user) throws Exception;
}

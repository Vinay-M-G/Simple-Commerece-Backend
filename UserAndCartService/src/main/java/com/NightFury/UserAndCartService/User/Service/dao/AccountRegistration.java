package com.NightFury.UserAndCartService.User.Service.dao;

import java.util.Map;

import javax.json.JsonObject;

public interface AccountRegistration {
	public boolean isExistingUser(String User);
	public Map<String, Object> createAccount(JsonObject account);
}

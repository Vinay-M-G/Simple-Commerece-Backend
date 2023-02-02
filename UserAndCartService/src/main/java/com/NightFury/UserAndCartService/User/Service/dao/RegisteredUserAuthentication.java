package com.NightFury.UserAndCartService.User.Service.dao;

import java.util.Map;

import javax.json.JsonObject;

public interface RegisteredUserAuthentication {
	public Map<String, Object> createRegisteredUserAction(JsonObject request);
}

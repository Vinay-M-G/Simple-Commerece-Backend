package com.NightFury.WelcomeService.Service.dao;

import java.util.Map;

public interface WelcomeServiceConfigurationsProvider {
	
	public Map<String, Boolean> getConfigurations();
	public Map<String, String> getContactDetails();
}

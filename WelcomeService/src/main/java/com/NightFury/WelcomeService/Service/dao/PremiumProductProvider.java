package com.NightFury.WelcomeService.Service.dao;

import java.util.List;
import java.util.Map;

public interface PremiumProductProvider {
	
	public String getListOfProducts();
	public List<Map<String, Object>> getPremiumProducts();
}

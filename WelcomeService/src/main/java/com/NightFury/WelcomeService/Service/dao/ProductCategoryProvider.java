package com.NightFury.WelcomeService.Service.dao;

import java.util.List;
import java.util.Map;

public interface ProductCategoryProvider {
	
	public String getListOfProductCategory(); 
	public List<Map<String,Object>> sendTailoredListOfCategory();
}

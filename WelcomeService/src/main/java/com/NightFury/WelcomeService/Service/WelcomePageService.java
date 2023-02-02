package com.NightFury.WelcomeService.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.WelcomeService.Service.impl.PremiumProductsDynamicHandler;
import com.NightFury.WelcomeService.Service.impl.ProductCategoryContentDynamicHandler;
import com.NightFury.WelcomeService.Service.impl.WelcomeServiceConfigurationDynamicHandler;
import com.NightFury.WelcomeService.Service.impl.WhyUsContentProviderDynamicHandler;

@Service
public class WelcomePageService {
	
	@Autowired
	WelcomeServiceConfigurationDynamicHandler welcomeServiceConfigurationDynamicHandler;
	
	@Autowired
	WhyUsContentProviderDynamicHandler whyUsContentProviderDynamicHandler;
	
	@Autowired
	ProductCategoryContentDynamicHandler productCategoryContentDynamicHandler;
	
	@Autowired
	PremiumProductsDynamicHandler premiumProductsDynamicHandler;
	
	public Map<String, Object> sendWelcomePageDetails(){
		
		Map<String, Object> response = new HashMap<String,Object>();
		response.putAll(welcomeServiceConfigurationDynamicHandler.getConfigurations());
		response.put("contactDetails" ,welcomeServiceConfigurationDynamicHandler.getContactDetails());
		response.put("contentForWhyUS", whyUsContentProviderDynamicHandler.getWhyUsContent());
		response.put("productCategoryAvailable", productCategoryContentDynamicHandler.sendTailoredListOfCategory());
		response.put("premiumProducts" , premiumProductsDynamicHandler.getPremiumProducts());
		return response;
		
	}
}

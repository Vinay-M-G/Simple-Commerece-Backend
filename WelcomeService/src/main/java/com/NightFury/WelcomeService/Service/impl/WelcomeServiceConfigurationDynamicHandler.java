package com.NightFury.WelcomeService.Service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.WelcomeService.Entity.WelcomePageConfigurationSchema;
import com.NightFury.WelcomeService.Repository.WelcomeServerConfigurationRepository;
import com.NightFury.WelcomeService.Service.dao.WelcomeServiceConfigurationsProvider;

@Service
public class WelcomeServiceConfigurationDynamicHandler implements WelcomeServiceConfigurationsProvider {
	
	public static final String EMAIL = "email";
	public static final String ADDRESS1= "Address1";
	public static final String CONTACT_NUMBER = "phoneNumber";
	
	@Autowired
	WelcomeServerConfigurationRepository welcomeServerConfigurationRepository;
	
	@Override
	public Map<String , Boolean> getConfigurations() {
		Map<String , Boolean> response = new HashMap<String ,Boolean>();
		List<WelcomePageConfigurationSchema> userConfigDetails = welcomeServerConfigurationRepository.findAll().stream().filter(element -> !(element.getpKey().contains(ADDRESS1) || element.getpKey().contains(CONTACT_NUMBER)
				|| element.getpKey().contains(EMAIL))).toList();
		
		userConfigDetails.stream().forEach(element -> {
			
			if(element.getpValue().equals("1") && element.getpKey() != null) {
				response.put(element.getpKey(), true);
			}
			else {
				response.put(element.getpKey(), false);
			}
		});
		return response;
	}

	@Override
	public Map<String, String> getContactDetails() {
		Map<String , String> response = new HashMap<String ,String>();
		
		List<WelcomePageConfigurationSchema> contactDetails = welcomeServerConfigurationRepository.findAll().stream().filter(element -> element.getpKey().contains(ADDRESS1) || element.getpKey().contains(CONTACT_NUMBER)
				|| element.getpKey().contains(EMAIL)).toList();
		 
		contactDetails.stream().forEach(element -> response.put(element.getpKey(), element.getpValue()));
		
		return response;
	}

}

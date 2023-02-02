package com.NightFury.WelcomeService.Service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.WelcomeService.Repository.WhyUsContentRepository;
import com.NightFury.WelcomeService.Service.dao.WhyUsContentProvider;

@Service
public class WhyUsContentProviderDynamicHandler implements WhyUsContentProvider {
	
	@Autowired
	WhyUsContentRepository whyUsContentRepository;

	@Override
	public List<Map<String, Object>> getWhyUsContent() {
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
		whyUsContentRepository.getWhyContentByOrder().stream().forEach(element ->{
			Map<String , Object> content = new HashMap<String , Object>();
			content.put("rank", element.getwRank());
			content.put("content", element.getwContent());
			response.add(content);
		});
		return response;
	}

}

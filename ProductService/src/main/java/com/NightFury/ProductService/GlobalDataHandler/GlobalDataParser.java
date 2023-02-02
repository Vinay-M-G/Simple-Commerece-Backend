package com.NightFury.ProductService.GlobalDataHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.NightFury.ProductService.ProductServiceApplication;

@Service
public class GlobalDataParser {
	public static final Logger logger = LoggerFactory.getLogger(ProductServiceApplication.class);
	
	public String dataParserToCommaAndQuotes(String content) {
		try {
			String categoryValueForQuery = new String();
			String[] catValues = content.split(",");
			for(int index = 0 ; index < catValues.length ; index++) {
				categoryValueForQuery = categoryValueForQuery + "'" + catValues[index] + "'" + ",";
				
			}
			return categoryValueForQuery.substring(0, categoryValueForQuery.length()-1);
	
		}catch(Exception e) {
			logger.error("Error while parsing the values sent by client." + e.toString());
			return "error";
		}
				
	}
}

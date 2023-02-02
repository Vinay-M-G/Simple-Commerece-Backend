package com.NightFury.UserAndCartService.JsonReader;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.NightFury.UserAndCartService.UserAndCartServiceApplication;

@Service
public class GlobalJsonReader {
	
	private static Logger logger = LoggerFactory.getLogger(UserAndCartServiceApplication.class);
	
	public JsonObject sendJsonObjectFromString(String content) {
		JsonObject object = Json.createObjectBuilder().build();
		try {
			JsonReader jsr = Json.createReader(new StringReader(content));
			object = jsr.readObject();
			jsr.close();
			
		}catch (Exception e) {
			logger.error("Unexpected Error Occured while parsing Json Body : Trace : "+ e.toString());
		}		
		
		return object;
	}
	
	public JsonArray sendJsonArrayFromString(String content) {
		JsonArray object = Json.createArrayBuilder().build();
		try {
			JsonReader jsr = Json.createReader(new StringReader(content));
			object = jsr.readArray();
			jsr.close();
			
		}catch (Exception e) {
			logger.error("Unexpected Error Occured while parsing Json Body : Trace : "+ e.toString());
		}		
		
		return object;
	}
}

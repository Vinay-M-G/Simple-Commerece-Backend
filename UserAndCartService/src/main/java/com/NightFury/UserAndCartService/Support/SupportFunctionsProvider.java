package com.NightFury.UserAndCartService.Support;

import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
public class SupportFunctionsProvider {
	
	private static final DecimalFormat DF = new DecimalFormat("0.00");
	
	public String generateUUID() {
		UUID id = UUID.randomUUID();
		return id.toString();
	}

	public Timestamp getCurrentDateTime() {
		Date date = new Date();
		Timestamp timeStamp = new Timestamp(date.getTime());
		return timeStamp;
	}
	
	public String errorMessageFormatter(String error) {
		return error.split(": ")[1].trim();
	}
	
	public Cookie cookieGenerator(String key , String value) {
		Cookie cookie = new Cookie(key , value);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		return cookie;
	}
	
	public void earseAllCookies(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
	    if (cookies != null)
	        for (Cookie cookie : cookies) {
	            cookie.setValue("");
	            cookie.setPath("/");
	            cookie.setMaxAge(0);
	            response.addCookie(cookie);
	        }
	}
	
	/*
	 * This function is used by cart populator functions whenever there is an update
	 * in cart entries
	 * 
	 * Extending the functionality for Address classes and Order Classes as well
	 * 
	 * @Author: Vinay M G
	 */
	public Map<String , Object> responseBuilderForCartUpdate(boolean processErrors , String message){
		Map<String , Object> responseBuild = new HashMap<>();
		if(!processErrors) {
			responseBuild.put("error", false);
			responseBuild.put("message", message);
			return responseBuild;
		}else {
			responseBuild.put("error", true);
			responseBuild.put("message", message);
			return responseBuild;
		}
		
	}
	
	public double formattedCostValue(double value) {
		DF.setRoundingMode(RoundingMode.HALF_UP);
		return Double.parseDouble(DF.format(value));
	}

}

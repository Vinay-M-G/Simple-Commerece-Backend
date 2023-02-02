package com.NightFury.WelcomeService.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NightFury.WelcomeService.Service.WelcomePageService;

@RestController
@RequestMapping("api/v1/welcome")
public class WelcomeServerController {
	
	@Autowired
	WelcomePageService welcomePageService;
	
	@GetMapping(value = "/requestdetails" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> sendDetailsforWelComePage() {
		Map<String, Object> serviceResponse = welcomePageService.sendWelcomePageDetails();
		return ResponseEntity.ok(serviceResponse);
	}
}

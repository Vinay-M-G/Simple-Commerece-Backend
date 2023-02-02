package com.NightFury.UserAndCartService.User.Controller;

import java.util.Map;

import javax.json.JsonObject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.NightFury.UserAndCartService.JsonReader.GlobalJsonReader;
import com.NightFury.UserAndCartService.Support.SupportFunctionsProvider;
import com.NightFury.UserAndCartService.User.AddressService.UserAddressService;
import com.NightFury.UserAndCartService.User.Service.impl.AccountRegistrationHandler;
import com.NightFury.UserAndCartService.User.Service.impl.GuestUserRegistrationAndLoginHandler;
import com.NightFury.UserAndCartService.User.Service.impl.RegisteredUserAuthenticationHandler;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
	
	@Autowired
	GlobalJsonReader globalJsonReader;
	
	@Autowired
	AccountRegistrationHandler accountRegistrationHandler;
	
	@Autowired
	GuestUserRegistrationAndLoginHandler guestUserRegistrationAndLoginHandler;
	
	@Autowired
	RegisteredUserAuthenticationHandler registeredUserAuthenticationHandler;
	
	@Autowired
	UserAddressService userAddressService;
	
	@Autowired
	SupportFunctionsProvider supportFunctionsProvider;
	
	@PostMapping(value = "/createguestuser" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> createGuestUserAction(@RequestBody String request , HttpServletResponse response){
		JsonObject requestBody = globalJsonReader.sendJsonObjectFromString(request);
		Map<String, Object> responseBody = guestUserRegistrationAndLoginHandler.createGuestUserAction(requestBody);
		if(!(boolean)responseBody.get("error")) {
			Cookie cookie = supportFunctionsProvider.cookieGenerator("guid", (String)responseBody.get("guid"));
			response.addCookie(cookie);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
		}
	}
	
	@PostMapping(value = "/createregistereduser" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> createRegisterUserAction(@RequestBody String request){
		JsonObject requestBody = globalJsonReader.sendJsonObjectFromString(request);
		Map<String, Object> responseBody = accountRegistrationHandler.createAccount(requestBody);
		if(!(boolean)responseBody.get("error")) {
			return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
		}
	}
	
	@PostMapping(value = "/reguserlogin" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> regUserLoginAction(@RequestBody String request , HttpServletResponse response){
		JsonObject requestBody = globalJsonReader.sendJsonObjectFromString(request);
		Map<String, Object> responseBody = registeredUserAuthenticationHandler.createRegisteredUserAction(requestBody);
		if(!(boolean)responseBody.get("error")) {
			Cookie cookie = supportFunctionsProvider.cookieGenerator("guid", (String)responseBody.get("guid"));
			response.addCookie(cookie);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
		}
	}
	
	@PostMapping(value = "/updateaddress" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> updateAddress(@RequestBody String request, @RequestHeader(value = "guid") String guid){
		JsonObject requestBody = globalJsonReader.sendJsonObjectFromString(request);
		Map<String, Object> responseBody = userAddressService.addAddress(requestBody, guid);
		
		if(!(boolean)responseBody.get("error")) {

			return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
		}
	}
	
	@GetMapping(value = "/getaddresslist" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getAddress(@RequestHeader(value = "guid") String guid, @RequestParam(value = "busnisessType") String busnisessType){
		
		Map<String, Object> responseBody = userAddressService.reteriveAddressList(guid, busnisessType);
		
		if(!(boolean)responseBody.get("error")) {

			return ResponseEntity.status(HttpStatus.OK).body(responseBody);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
		}
	}

}

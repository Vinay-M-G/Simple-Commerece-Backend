package com.NightFury.UserAndCartService.User.Service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.NightFury.UserAndCartService.Cart.Service.impl.CartIdCreationHandler;
import com.NightFury.UserAndCartService.Support.SupportFunctionsProvider;
import com.NightFury.UserAndCartService.User.Repository.BaseUserRepository;
import com.NightFury.UserAndCartService.User.Service.dao.RegisteredUserAuthentication;

@Service
public class RegisteredUserAuthenticationHandler implements RegisteredUserAuthentication {
	
	public static final String ERROR = "error";
	public static final String AUTH_STATUS = "authStatus";
	public static final String ERROR_MSG = "errorMessage";
	public static final String GUID = "guid";
	
	@Autowired
	UserSecurityHandler userSecurityHandler;
	
	@Autowired
	SupportFunctionsProvider supportFunctionsProvider;
	
	@Autowired
	BaseUserRepository baseUserRepository;
	
	@Autowired
	CartIdCreationHandler cartIdCreationHandler;
	
	@Override
	public Map<String, Object> createRegisteredUserAction(JsonObject request) {
		Map<String ,Object> response = new HashMap<String , Object>();
		
		String emailId = request.getString("email");
		String password = request.getString("password");
		
		boolean authResult = false;
		
		if(null != emailId && null!= password) {
			try {
				authResult = userSecurityHandler.login(emailId, password);
				
				if(authResult) {
					baseUserRepository.updateTimeForExistingUser(emailId);
					response.put(ERROR, false);
					response.put(AUTH_STATUS, "Success");
					response.put(ERROR_MSG, null);
					response.put(GUID, cartIdCreationHandler.createNewCartId(emailId));
				}
				
			}catch(BadCredentialsException ex) {
				
				response.put(ERROR, true);
				response.put(AUTH_STATUS, "Failed");
				response.put(ERROR_MSG, supportFunctionsProvider.errorMessageFormatter(ex.toString()));
				
				
			}catch(UsernameNotFoundException exp) {
				
				response.put(ERROR, true);
				response.put(AUTH_STATUS, "Failed");
				response.put(ERROR_MSG, supportFunctionsProvider.errorMessageFormatter(exp.toString()));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return response;
	}

}

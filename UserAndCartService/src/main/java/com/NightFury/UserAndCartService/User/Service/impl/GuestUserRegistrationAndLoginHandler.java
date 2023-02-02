package com.NightFury.UserAndCartService.User.Service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.NightFury.UserAndCartService.Cart.Service.impl.CartIdCreationHandler;
import com.NightFury.UserAndCartService.Support.SupportFunctionsProvider;
import com.NightFury.UserAndCartService.User.Entity.BaseUserModel;
import com.NightFury.UserAndCartService.User.ExceptionsHandler.RegisteredUserAlreadyExist;
import com.NightFury.UserAndCartService.User.Repository.BaseUserRepository;
import com.NightFury.UserAndCartService.User.Service.dao.GuestUserRegistrationAndLogin;

@Service
public class GuestUserRegistrationAndLoginHandler implements GuestUserRegistrationAndLogin {
	
	public static final String GUEST_ANONYMOUS_PASSWORD = "anonymous";
	public static final String ENCODED_ANONYMOUS_PASSWORD = "$2a$10$o0XMY3CvbgJrG3OPMsVoZOhWcaI.Kl4fSxmLoj3NpnOmCIe9HjEoO";
	public static final String REG_USER_EXIST_MESSAGE = "Looks like you have already have a registered user account, Login to get Exicted Offers";
	public static final String DEFAULT_USER_ROLE = "USER";
	public static final String USER_TYPE = "GUEST";
	public static final String ERROR = "error";
	public static final String AUTH_STATUS = "authStatus";
	public static final String ERROR_MSG = "errorMessage";
	public static final String GUID = "guid";
	
	@Autowired
	BaseUserRepository baseUserRepository;
	
	@Autowired
	UserSecurityHandler userSecurityHandler;
	
	@Autowired
	SupportFunctionsProvider supportFunctionsProvider;
	
	@Autowired
	CartIdCreationHandler cartIdCreationHandler;
	
	@Override
	public boolean isExistingUser(String User) {
		List<BaseUserModel> model = baseUserRepository.getGuestUsersBasedOnEmail(User);
		if(model.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
	
	@ExceptionHandler(value = RegisteredUserAlreadyExist.class)
	@Override
	public boolean isRegisteredUser(String user){
		List<BaseUserModel> model = baseUserRepository.getRegisteredUsersBasedOnEmail(user);
		if(model.isEmpty()) {
			return false;
		}else {
			throw new RegisteredUserAlreadyExist(REG_USER_EXIST_MESSAGE);
		}
	}

	@Override
	public Map<String, Object> createGuestUserAction(JsonObject request){
		Map<String ,Object> response = new HashMap<String , Object>();
		String emailId = request.getString("email");
		boolean authResult = false;
		
		try {
			if(!isExistingUser(emailId) && !isRegisteredUser(emailId)) {
				BaseUserModel baseUserModel = new BaseUserModel();
				
				baseUserModel.setSecretKey(ENCODED_ANONYMOUS_PASSWORD);
				baseUserModel.setEmail(emailId);
				baseUserModel.setFirstName(request.getString("firstName"));
				baseUserModel.setLastName(request.getString("lastName"));
				
				baseUserModel.setCreatedAt(supportFunctionsProvider.getCurrentDateTime());
				baseUserModel.setUpdatedAt(supportFunctionsProvider.getCurrentDateTime());
				
				baseUserModel.setUuid(supportFunctionsProvider.generateUUID());
				baseUserModel.setUserType(USER_TYPE);
				baseUserModel.setRoleName(DEFAULT_USER_ROLE);
				
				baseUserRepository.save(baseUserModel);
				
				authResult = userSecurityHandler.login(emailId, GUEST_ANONYMOUS_PASSWORD);
				
			}else {
				baseUserRepository.updateTimeForExistingUser(emailId);
				authResult = userSecurityHandler.login(emailId, GUEST_ANONYMOUS_PASSWORD);
			}
			
			
		}catch(RegisteredUserAlreadyExist ex) {
			response.put(ERROR, true);
			response.put(AUTH_STATUS, "Failed");
			response.put(ERROR_MSG, supportFunctionsProvider.errorMessageFormatter(ex.toString()));
			return response;
		}
		
		if(authResult) {
			response.put(ERROR, false);
			response.put(AUTH_STATUS, "Success");
			response.put(ERROR_MSG, null);
			response.put(GUID, cartIdCreationHandler.createNewCartId(emailId));
		}else {
			response.put(ERROR, true);
			response.put(AUTH_STATUS, "Failed");
			response.put(ERROR_MSG, "Bad credentials");
		}
		
		return response;
	}

}

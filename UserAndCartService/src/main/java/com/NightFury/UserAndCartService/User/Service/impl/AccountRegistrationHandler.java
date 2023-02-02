package com.NightFury.UserAndCartService.User.Service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.NightFury.UserAndCartService.Support.SupportFunctionsProvider;
import com.NightFury.UserAndCartService.User.Entity.BaseUserModel;
import com.NightFury.UserAndCartService.User.Repository.BaseUserRepository;
import com.NightFury.UserAndCartService.User.Service.dao.AccountRegistration;

@Service
public class AccountRegistrationHandler implements AccountRegistration{
	
	private static final String ERROR = "error";
	private static final String ERROR_MESSAGE = "errorMessage";
	public static final String DEFAULT_USER_ROLE = "USER";
	public static final String USER_TYPE = "REGISTERED";
	
	@Autowired
	BaseUserRepository baseUserRepository;
	
	@Autowired
	SupportFunctionsProvider supportFunctionsProvider;
	
	@Override
	public boolean isExistingUser(String User) {
		List<BaseUserModel> model = baseUserRepository.getRegisteredUsersBasedOnEmail(User);
		if(model.isEmpty()) {
			return false;
		}else {
			return true;
		}
		
	}

	@Override
	public Map<String, Object> createAccount(JsonObject account) {
		Map<String ,Object> response = new HashMap<String , Object>();
		String emailId = account.getString("email");
		
		if(!isExistingUser(emailId) && null != emailId) {
			BaseUserModel baseUserModel = new BaseUserModel();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
			baseUserModel.setSecretKey(encoder.encode(account.getString("password")));
			baseUserModel.setEmail(emailId);
			baseUserModel.setFirstName(account.getString("firstName"));
			baseUserModel.setLastName(account.getString("lastName"));
			
			baseUserModel.setCreatedAt(supportFunctionsProvider.getCurrentDateTime());
			baseUserModel.setUpdatedAt(supportFunctionsProvider.getCurrentDateTime());
			
			baseUserModel.setUuid(supportFunctionsProvider.generateUUID());
			baseUserModel.setUserType(USER_TYPE);
			baseUserModel.setRoleName(DEFAULT_USER_ROLE);
			
			baseUserRepository.save(baseUserModel);
			
			response.put(ERROR, false);
			response.put(ERROR_MESSAGE, null);
			
			return response;
			
		}else {
			response.put(ERROR, true);
			response.put(ERROR_MESSAGE, "An Account Exist already or Email id not valid, Please validate!!!");
		}
		return response;
	}
	
}
  
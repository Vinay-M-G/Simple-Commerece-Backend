package com.NightFury.UserAndCartService.User.AddressService.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.json.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.NightFury.UserAndCartService.UserAndCartServiceApplication;
import com.NightFury.UserAndCartService.Support.SupportFunctionsProvider;
import com.NightFury.UserAndCartService.User.AddressService.dao.UserAddress;
import com.NightFury.UserAndCartService.User.Entity.CartAddressModel;
import com.NightFury.UserAndCartService.User.Entity.UserAddressModel;
import com.NightFury.UserAndCartService.User.Enum.AddressType;
import com.NightFury.UserAndCartService.User.Enum.BusinessType;
import com.NightFury.UserAndCartService.User.ExceptionsHandler.CompanyNameNotFound;
import com.NightFury.UserAndCartService.User.Repository.CartAddressRepository;
import com.NightFury.UserAndCartService.User.Repository.UserAddressRepository;

@Service
public class UserAddressHandler implements UserAddress {
	public static final Logger logger = LoggerFactory.getLogger(UserAndCartServiceApplication.class);
	
	@Autowired
	SupportFunctionsProvider supportFunctionsProvider;
	
	@Autowired
	UserAddressRepository userAddressRepository;
	
	@Autowired
	CartAddressRepository cartAddressRepository;
	
	private String getAddressTypeValue(boolean isBillingAddress) {
		
		return isBillingAddress ? AddressType.BILLING.toString() : AddressType.SHIPPING.toString();
	}
	
	private String getBusnisessTypeValue(boolean isBusinessOrder) {
		
		return isBusinessOrder ? BusinessType.B2B.toString() : BusinessType.B2C.toString();
	}
	
	private boolean isValidCompanyName(UserAddressModel userAddressModel) {
		
		if (userAddressModel.getBusinessType().equals(BusinessType.B2B.toString())) {
			
			if (userAddressModel.getCompanyName() == null) {
				return false;
			}
			
			if (userAddressModel.getCompanyName() != null && userAddressModel.getCompanyName().isBlank()) {
				return false;
			}
			
		}
		return true;
	}
	
	private CartAddressModel cartAddressParser(JsonObject request, String guid){
		
		CartAddressModel cartAddressModel = new CartAddressModel();
		
		try {
			String addressType = getAddressTypeValue(request.getBoolean("isBillingAddress"));
			Optional<String> existingPk = cartAddressRepository.getSelectedAddressValue(guid, addressType);
			
			if(existingPk.isPresent()) {
				cartAddressModel.setPk(existingPk.get());
			}else {
				cartAddressModel.setPk(supportFunctionsProvider.generateUUID());
			}
			
			cartAddressModel.setAddressTitle(request.getString("addressTitle"));
			
			cartAddressModel.setAddressType(addressType);
			cartAddressModel.setBusinessType(getBusnisessTypeValue(request.getBoolean("isBusinessOrder")));
			
			cartAddressModel.setUpdatedAt(supportFunctionsProvider.getCurrentDateTime());
			cartAddressModel.setGuid(guid);
			
		}catch(Exception ex) {
			
			logger.warn("Exception occured while creating Cart Address data Model: " + ex.toString());
		}
		
		return cartAddressModel;
	}
	
	private UserAddressModel userAddressParser(JsonObject request, String emailId) {
		UserAddressModel userAddressModel = new UserAddressModel();

		try {
			
			String addressTitle = request.getString("addressTitle");
			String addressType = getAddressTypeValue(request.getBoolean("isBillingAddress"));
			String busnisessType = getBusnisessTypeValue(request.getBoolean("isBusinessOrder"));
			
			Optional<String> existingPk = userAddressRepository.getExistingAddressId(emailId, addressType, addressTitle, busnisessType);
			
			if(existingPk.isPresent()) {
				userAddressModel.setPk(existingPk.get());
			}else {
				userAddressModel.setPk(supportFunctionsProvider.generateUUID());
			}
			
			userAddressModel.setLine1(request.getString("line1"));
			userAddressModel.setLine2(request.getString("line2"));
			userAddressModel.setEmailId(emailId);
			
			userAddressModel.setCity(request.getString("city"));
			userAddressModel.setState(request.getString("state"));
			userAddressModel.setPincode(request.getString("pincode"));
			userAddressModel.setCountry(request.getString("country"));
			
			userAddressModel.setPhoneNumber(request.getString("phoneNumber"));
			userAddressModel.setCountryCode(request.getString("countryCode"));
			
			userAddressModel.setAddressTitle(addressTitle);
			userAddressModel.setAddressType(addressType);
			userAddressModel.setBusinessType(busnisessType);
			
			userAddressModel.setUpdatedAt(supportFunctionsProvider.getCurrentDateTime());
			
			Optional<String> companyName = Optional.ofNullable(request.getString("companyName"));
			if(companyName.isPresent()) {
				userAddressModel.setCompanyName(companyName.get());
			}
			

		}catch(NullPointerException ex) {
			
			logger.warn("Missing some data Attributes for user model" + ex.toString());
		}catch(Exception ex) {
			
			logger.warn("Exception occured while creating User Address data Model: " + ex.toString());
		}
		
		return userAddressModel;
		
	}
	
	@Override
	public List<UserAddressModel> getUserAddressList(String emailId, String businessType) {
		
		List<UserAddressModel> addressList = new ArrayList<>();
		addressList = userAddressRepository.findByEmailIdAndBusinessType(emailId, businessType);
		return addressList;
	}
	
	@ExceptionHandler(value = CompanyNameNotFound.class)
	@Override
	public boolean updateUserAddress(JsonObject requestBody, String emailId) {
		
		try {
			UserAddressModel userAddressModel = userAddressParser(requestBody, emailId);
			
			if(!isValidCompanyName(userAddressModel)) {
				throw new CompanyNameNotFound("Company Name is invalid or company name is missing");
			}
			
			userAddressRepository.save(userAddressModel);
			return true;
			
		}catch(CompanyNameNotFound ex) {
			
			throw ex;
		}catch(Exception ex) {
			
			logger.warn("Exception occured while saving User Address data Model: " + ex.toString());
			return false;
		}
		
	}

	@Override
	public boolean updateUserAddressForCart(String guid, JsonObject requestBody) {
		try {
			
			CartAddressModel cartAddressModel = cartAddressParser(requestBody, guid);
			cartAddressRepository.saveAndFlush(cartAddressModel);
			return true;
			
		}catch(Exception ex) {
			
			logger.warn("Exception occured while saving User Address data Model: " + ex.toString());
			return false;
		}

	}

}

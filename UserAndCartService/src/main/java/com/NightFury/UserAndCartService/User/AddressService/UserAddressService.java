package com.NightFury.UserAndCartService.User.AddressService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.json.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.NightFury.UserAndCartService.UserAndCartServiceApplication;
import com.NightFury.UserAndCartService.Cart.Entity.CartUserAssociationModel;
import com.NightFury.UserAndCartService.Cart.Repository.CartUserAssociationRepository;
import com.NightFury.UserAndCartService.Support.SupportFunctionsProvider;
import com.NightFury.UserAndCartService.User.AddressService.impl.UserAddressHandler;
import com.NightFury.UserAndCartService.User.Entity.UserAddressModel;
import com.NightFury.UserAndCartService.User.ExceptionsHandler.CompanyNameNotFound;

@Service
public class UserAddressService {
	
	public static final Logger logger = LoggerFactory.getLogger(UserAndCartServiceApplication.class);
	
	@Value("${address.update.success}")
	private String addressUpdatedSuccessfully;
	
	@Value("${address.update.failed}")
	private String addressNotUpdatedSuccessfully;
	
	@Value("${address.get.success}")
	private String addressValuesFound;
	
	@Value("${address.get.failed}")
	private String noAddressValuesFound;
	
	@Value("${address.emailid.notfound}")
	private String emailNotFound;
	
	@Autowired
	CartUserAssociationRepository cartUserAssociationRepository;
	
	@Autowired
	UserAddressHandler userAddressHandler;
	
	@Autowired
	SupportFunctionsProvider supportFunctionsProvider;
	
	public Map<String , Object> addAddress(JsonObject requestBody, String guid){
		
		Map<String , Object> response = new HashMap<>();
		
		try {
			
			Optional<CartUserAssociationModel> cartUserAssociationModel = cartUserAssociationRepository.findById(guid);
			boolean addressUpdate = false;
			
			if(cartUserAssociationModel.isPresent()) {
				String emailId = cartUserAssociationModel.get().getUserId();
				addressUpdate = userAddressHandler.updateUserAddress(requestBody, emailId);
				
				if(addressUpdate) {
					addressUpdate = userAddressHandler.updateUserAddressForCart(guid, requestBody);
				}
				
			}
			
			if(addressUpdate) {
				
				logger.info("Address updated successfully for: " + guid);
				response = supportFunctionsProvider.responseBuilderForCartUpdate(false, addressUpdatedSuccessfully);
			}else {
				
				response = supportFunctionsProvider.responseBuilderForCartUpdate(true, addressNotUpdatedSuccessfully);
			}
			
		}catch(CompanyNameNotFound ex) {
			
			response = supportFunctionsProvider.responseBuilderForCartUpdate(true, ex.toString());
			
		}catch(Exception ex) {
			
			logger.error("An error occured while updating Address data: " + ex.toString());
		}

		return response;
	}
	
	public Map<String, Object> reteriveAddressList(String guid, String busnisessType){
		Map<String, Object> response = new HashMap<String, Object>();
		
		String emailId = null;
		Optional<CartUserAssociationModel> cartUserAssociationModel = cartUserAssociationRepository.findById(guid);
		
		if(cartUserAssociationModel.isPresent()) {
			emailId = cartUserAssociationModel.get().getUserId();
		}
		
		if(emailId != null) {
			List<UserAddressModel> addressList = userAddressHandler.getUserAddressList(emailId, busnisessType);
			
			
			if(!addressList.isEmpty()) {
				response = supportFunctionsProvider.responseBuilderForCartUpdate(false, addressValuesFound);
				response.put("addressValues", addressList);
				response.put("emailId" , emailId);
				return response;
			}else {
				
				response = supportFunctionsProvider.responseBuilderForCartUpdate(false, noAddressValuesFound);
				response.put("emailId" , emailId);
				return response;
			}
			
		}
		
		response = supportFunctionsProvider.responseBuilderForCartUpdate(true, emailNotFound);
		return response;
	}
	
	public Map<String, Object> getSelectedAddressElementsForCart(String guid){
		Map<String, Object> response = new HashMap<String, Object>();
		
		String emailId = null;
		Optional<CartUserAssociationModel> cartUserAssociationModel = cartUserAssociationRepository.findById(guid);
		
		if(cartUserAssociationModel.isPresent()) {
			emailId = cartUserAssociationModel.get().getUserId();
		}
		
		if(emailId != null) {
			List<UserAddressModel> cartAddressList = userAddressHandler.getSelectedCartAddress(guid, emailId);
			
			if(!cartAddressList.isEmpty()) {
				response = supportFunctionsProvider.responseBuilderForCartUpdate(false, addressValuesFound);
				response.put("addressValues", cartAddressList);
				response.put("emailId" , emailId);
				
				logger.info("Cart Address Response Body prepared for cart : " + guid);
				return response;
				
			}else {
				
				response = supportFunctionsProvider.responseBuilderForCartUpdate(false, noAddressValuesFound);
				response.put("emailId" , emailId);
				return response; 
			}
		}
		
		response = supportFunctionsProvider.responseBuilderForCartUpdate(true, emailNotFound);
		return response;
	}

}

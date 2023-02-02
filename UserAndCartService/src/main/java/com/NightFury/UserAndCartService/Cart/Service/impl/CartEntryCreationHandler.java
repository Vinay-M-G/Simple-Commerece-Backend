package com.NightFury.UserAndCartService.Cart.Service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.UserAndCartService.UserAndCartServiceApplication;
import com.NightFury.UserAndCartService.Cart.Entity.CartDeliveryServiceEntryModel;
import com.NightFury.UserAndCartService.Cart.Entity.CartProductEntryModel;
import com.NightFury.UserAndCartService.Cart.Entity.CartProductServiceEntryModel;
import com.NightFury.UserAndCartService.Cart.Repository.CartDeliveryServiceEntryModelRepository;
import com.NightFury.UserAndCartService.Cart.Repository.CartProductEntryRepository;
import com.NightFury.UserAndCartService.Cart.Repository.CartProductServiceEntryRepository;
import com.NightFury.UserAndCartService.Cart.Service.dao.CartPopulator;
import com.NightFury.UserAndCartService.Support.SupportFunctionsProvider;

@Service 
public class CartEntryCreationHandler implements CartPopulator {
	
	private static Logger logger = LoggerFactory.getLogger(UserAndCartServiceApplication.class);
	
	@Autowired
	CartProductEntryRepository cartProductEntryRepository;
	
	@Autowired
	CartProductServiceEntryRepository cartProductServiceEntryRepository;
	
	@Autowired
	SupportFunctionsProvider supportFunctionsProvider;
	
	@Autowired
	CartDeliveryServiceEntryModelRepository cartDeliveryServiceEntryModelRepository;
	
	@Override
	public Map<String, Object> addProduct(String productCode, String guid) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			List<CartProductEntryModel> uniqueProductEntry = cartProductEntryRepository.reteriveProductEntry(guid, productCode);
			
			if(uniqueProductEntry.isEmpty()) {
				CartProductEntryModel productEntry = new CartProductEntryModel();
				productEntry.setGuid(guid);
				productEntry.setProductCode(productCode);
				productEntry.setQuantity(1);
				productEntry.setPk(supportFunctionsProvider.generateUUID());
				
				cartProductEntryRepository.save(productEntry);
				
				response = supportFunctionsProvider.responseBuilderForCartUpdate(false, "ProductId: " + productCode + " added successfully");
				
			}
			
			if(uniqueProductEntry.size() == 1) {
				int newQuantity = uniqueProductEntry.get(0).getQuantity() + 1;
				int check = cartProductEntryRepository.updateProductQuantity(newQuantity, guid, productCode);
				if(check == 1) {
					response = supportFunctionsProvider.responseBuilderForCartUpdate(false, "Quantity Updated for ProductId: " + productCode + " successfully");
				}else {
					response = supportFunctionsProvider.responseBuilderForCartUpdate(true, "Quantity Update failed for ProductId: " + productCode);
				}
				
			}
			
		}catch(Exception ex) {
			response = supportFunctionsProvider.responseBuilderForCartUpdate(true,"An error occured while updating Product: " + productCode);
			logger.error(ex.toString());
		}
		
		return response;
	}

	@Override
	public Map<String, Object> updateProduct(String productCode, String guid, int Quantity) {
		Map<String, Object> response = new HashMap<>();

		try {
			if(Quantity > 0) {
				int check = cartProductEntryRepository.updateProductQuantity(Quantity, guid, productCode);
				if(check == 1) {
					
					response = supportFunctionsProvider.responseBuilderForCartUpdate(false, "Quantity Updated for ProductId: " + productCode + " successfully");
				}else {
					
					response = supportFunctionsProvider.responseBuilderForCartUpdate(true, "Quantity Update failed for ProductId: " + productCode);
				}
				
			}else {
				int removalCheck = cartProductEntryRepository.removeProductEntry(guid, productCode);
				if(removalCheck == 1) {
					
					response = supportFunctionsProvider.responseBuilderForCartUpdate(false, "ProductId: " + productCode + " removed successfully");
				}else {
					
					response = supportFunctionsProvider.responseBuilderForCartUpdate(true, "product removal failed for ProductId: " + productCode);
				}
			}
			
			
		}catch(Exception ex) {
			response = supportFunctionsProvider.responseBuilderForCartUpdate(true,"An error occured while updating Product: " + productCode);
			logger.error(ex.toString());
		}
		return response;
	}

	@Override
	public Map<String, Object> addService(String productCode, String guid, String serviceId) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			String serviceEntryCheck = cartProductServiceEntryRepository.checkServiceDuplicateEntry(guid, serviceId, productCode);
			
			if(serviceEntryCheck == null) {
				CartProductServiceEntryModel cartProductServiceEntryModel = new CartProductServiceEntryModel();
				
				cartProductServiceEntryModel.setGuid(guid);
				cartProductServiceEntryModel.setPk(supportFunctionsProvider.generateUUID());
				cartProductServiceEntryModel.setProductCode(productCode);
				cartProductServiceEntryModel.setServiceCode(serviceId);
				
				cartProductServiceEntryRepository.save(cartProductServiceEntryModel);
			}
			
			response = supportFunctionsProvider.responseBuilderForCartUpdate(false, "ServiceId: " + serviceId + " for ProductId "
					+ productCode + " added successfully");
			
		}catch(Exception ex) {
			response = supportFunctionsProvider.responseBuilderForCartUpdate(true,"An error occured while updating Service: " + serviceId);
			logger.error(ex.toString());
		}
		
		return response;
	}

	@Override
	public Map<String, Object> removeService(String productCode, String guid, String serviceId) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			int serviceEntriesRemoved = cartProductServiceEntryRepository.removeServiceEntryFromCart(guid, serviceId, productCode);
			
			if(serviceEntriesRemoved == 1 ) {
				response = supportFunctionsProvider.responseBuilderForCartUpdate(false, "ServiceId: " + serviceId + " for ProductId "
						+ productCode + " removed successfully");
			}else {
				response = supportFunctionsProvider.responseBuilderForCartUpdate(false, "ServiceId: " + serviceId + " doesn't exist in the cart");
			}
			
		}catch(Exception ex) {
			response = supportFunctionsProvider.responseBuilderForCartUpdate(true,"An error occured while updating Service: " + serviceId);
			logger.error(ex.toString());
		}
		return response;
	}

	@Override
	public Map<String, Object> addDeliveryService(String serviceId, String guid) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			CartDeliveryServiceEntryModel cartDeliveryServiceEntryModel = new CartDeliveryServiceEntryModel();
			cartDeliveryServiceEntryModel.setGuid(guid);
			cartDeliveryServiceEntryModel.setServiceCode(serviceId);
			cartDeliveryServiceEntryModel.setPk(supportFunctionsProvider.generateUUID());
			
			cartDeliveryServiceEntryModelRepository.save(cartDeliveryServiceEntryModel);
			
			response = supportFunctionsProvider.responseBuilderForCartUpdate(false, "Delivery ServiceId: " + serviceId + 
					  " added successfully");
			
		}catch(Exception ex) {
			
			response = supportFunctionsProvider.responseBuilderForCartUpdate(true, "Error occured while updating Delivery Service");
			logger.error(ex.toString());
		}
		return response;
	}


}

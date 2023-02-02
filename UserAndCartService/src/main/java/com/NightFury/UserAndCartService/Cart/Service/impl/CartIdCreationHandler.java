package com.NightFury.UserAndCartService.Cart.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.UserAndCartService.Cart.Entity.CartUserAssociationModel;
import com.NightFury.UserAndCartService.Cart.Repository.CartUserAssociationRepository;
import com.NightFury.UserAndCartService.Cart.Service.dao.CartCreator;
import com.NightFury.UserAndCartService.Support.SupportFunctionsProvider;

@Service
public class CartIdCreationHandler implements CartCreator {
	
	@Autowired
	SupportFunctionsProvider supportFunctionsProvider;
	
	@Autowired
	CartUserAssociationRepository cartUserAssociationRepository;
	
	@Override
	public String getExistingCartId(String emailId) {
		return cartUserAssociationRepository.reteriveCartId(emailId);
	}

	@Override
	public String createNewCartId(String emailId) {
		
		String cartId = getExistingCartId(emailId);
		
		if(null == cartId ) {
			CartUserAssociationModel cartUserAssociationModel = new CartUserAssociationModel();
			String guid = supportFunctionsProvider.generateUUID();
			
			cartUserAssociationModel.setGuid(guid);
			cartUserAssociationModel.setCreatedAt(supportFunctionsProvider.getCurrentDateTime());
			cartUserAssociationModel.setUserId(emailId);
			cartUserAssociationModel.setConvertedToOrder(false);
			
			cartUserAssociationRepository.save(cartUserAssociationModel);
			
			return guid;
			
		}else {
			return cartId;
		}
		
	}


}

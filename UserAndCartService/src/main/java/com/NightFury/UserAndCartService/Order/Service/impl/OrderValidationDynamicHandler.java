package com.NightFury.UserAndCartService.Order.Service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NightFury.UserAndCartService.Cart.Entity.CartProductEntryModel;
import com.NightFury.UserAndCartService.Cart.Entity.CartUserAssociationModel;
import com.NightFury.UserAndCartService.Cart.Repository.CartUserAssociationRepository;
import com.NightFury.UserAndCartService.Order.Entity.CartOrderAssociationModel;
import com.NightFury.UserAndCartService.Order.Repository.CartOrderAssociationRepository;
import com.NightFury.UserAndCartService.Order.Service.dao.OrderValidator;

@Service
public class OrderValidationDynamicHandler implements OrderValidator {
	
	@Autowired
	CartUserAssociationRepository cartUserAssociationRepository;
	
	@Autowired
	CartOrderAssociationRepository cartOrderAssociationRepository;
	
	@Override
	public boolean validateCartForAvailableQuantity(String product, List<CartProductEntryModel> cartProducts, int availableQuantity) {
		
		boolean flag = false;
		
		for(CartProductEntryModel element : cartProducts) {
			
			if(element.getProductCode().equals(product)) {
				
				if(element.getQuantity() > availableQuantity) {
					flag = false;
					break;
				}else {
					flag = true;
					break;
				}
			}
		}
		
		return flag;
		
	}

	@Override
	public boolean validateCartForPriceChange(double payablePrice, Map<String, Object> basket) {
		try {
			
			Map<String, Double> cartCost = (Map<String, Double>) basket.get("basketCostSummary");
			double finalPrice = cartCost.get("orderTotalWithDiscount");
			
			if(finalPrice != payablePrice) {
				return false;
			}
			
		}catch(ClassCastException ex) {
			
			
		}
		
		return true;
	}

	@Override
	public boolean isACart(String guid) {
		Optional<CartUserAssociationModel> cartModel = cartUserAssociationRepository.findById(guid);
		
		if(cartModel.isEmpty()) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean isOrderExist(String guid) {
		Optional<CartOrderAssociationModel> cartOrderModel = cartOrderAssociationRepository.findById(guid);
		
		if(cartOrderModel.isPresent()) {
			return true;
		}
		
		return false;
	}

}

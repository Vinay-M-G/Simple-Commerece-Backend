package com.NightFury.UserAndCartService.Order.Service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.NightFury.UserAndCartService.Order.Entity.CartOrderAssociationModel;
import com.NightFury.UserAndCartService.Order.Entity.OrderAddressModel;
import com.NightFury.UserAndCartService.Order.Entity.OrderCostSummaryModel;
import com.NightFury.UserAndCartService.Order.Entity.OrderEntryModel;
import com.NightFury.UserAndCartService.Order.ExceptionsHandler.OrderNotFound;
import com.NightFury.UserAndCartService.Order.Repository.CartOrderAssociationRepository;
import com.NightFury.UserAndCartService.Order.Repository.OrderAddressRepository;
import com.NightFury.UserAndCartService.Order.Repository.OrderCostSummaryRepository;
import com.NightFury.UserAndCartService.Order.Repository.OrderEntryRepository;
import com.NightFury.UserAndCartService.Order.Service.dao.OrderProvider;

@Service
public class OrderPopulator implements OrderProvider {
	
	private static final String ORDER_NOT_FOUND = "Requested Order Record is not found";
	
	@Autowired
	CartOrderAssociationRepository cartOrderAssociationRepository;
	
	@Autowired
	OrderAddressRepository orderAddressRepository;
	
	@Autowired
	OrderCostSummaryRepository orderCostSummaryRepository;
	
	@Autowired
	OrderEntryRepository orderEntryRepository;
	
	@ExceptionHandler( value = OrderNotFound.class)
	@Override
	public List<OrderEntryModel> getOrderEntries(String orderId) {
		
		Optional<List<OrderEntryModel>> orderEntries = orderEntryRepository.findByOrderId(orderId);
		
		if(orderEntries.get().size() == 0) {
			throw new OrderNotFound(ORDER_NOT_FOUND);
		}
		
		return orderEntries.get();
	}
	
	@ExceptionHandler( value = OrderNotFound.class)
	@Override
	public List<OrderAddressModel> getOrderAddressEntries(String orderId) {
		Optional<List<OrderAddressModel>> orderAddressEntry = orderAddressRepository.findByOrderId(orderId);
		
		if(orderAddressEntry.get().size() == 0) {
			throw new OrderNotFound(ORDER_NOT_FOUND);
		}
		
		return orderAddressEntry.get();
	}
	
	@ExceptionHandler( value = OrderNotFound.class)
	@Override
	public List<OrderCostSummaryModel> getOrderCostSummary(String orderId) {
		
		Optional<List<OrderCostSummaryModel>> orderCostSummary = orderCostSummaryRepository.findByOrderId(orderId);
		
		if(orderCostSummary.get().size() == 0) {
			throw new OrderNotFound(ORDER_NOT_FOUND);
		}
		
		return orderCostSummary.get();
	}
	
	@ExceptionHandler( value = OrderNotFound.class)
	@Override
	public List<CartOrderAssociationModel> getCartOrderAssociationModel(String orderId) {
		
		Optional<List<CartOrderAssociationModel>> cartOrderAssociation = cartOrderAssociationRepository.findByOrderId(orderId);
		
		if(cartOrderAssociation.get().size() == 0) {
			throw new OrderNotFound(ORDER_NOT_FOUND);
		}
		
		return cartOrderAssociation.get();
	
	}
	
	
}

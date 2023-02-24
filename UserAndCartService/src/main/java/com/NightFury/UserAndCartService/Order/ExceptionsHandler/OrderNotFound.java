package com.NightFury.UserAndCartService.Order.ExceptionsHandler;

@SuppressWarnings("serial")
public class OrderNotFound extends RuntimeException{
	
	public OrderNotFound(String message) {
		super(message);
	}
}

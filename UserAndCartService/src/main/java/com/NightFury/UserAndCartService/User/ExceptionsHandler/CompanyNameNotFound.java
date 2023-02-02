package com.NightFury.UserAndCartService.User.ExceptionsHandler;

@SuppressWarnings("serial")
public class CompanyNameNotFound extends RuntimeException{
	public CompanyNameNotFound(String message) {
		super(message);
	}
}

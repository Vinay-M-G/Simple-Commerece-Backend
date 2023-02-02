package com.NightFury.UserAndCartService.User.ExceptionsHandler;

@SuppressWarnings("serial")
public class RegisteredUserAlreadyExist extends RuntimeException{
	public RegisteredUserAlreadyExist(String message) {
		super(message);
	}

}

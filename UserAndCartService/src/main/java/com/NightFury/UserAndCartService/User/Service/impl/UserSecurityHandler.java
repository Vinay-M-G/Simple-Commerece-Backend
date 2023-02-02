package com.NightFury.UserAndCartService.User.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityHandler{
	
	public static final String BAD_CRED_MESSAGE = "Invalid Password !!!";
	public static final String GUEST_ANONYMOUS_PASSWORD = "anonymous";
	
	@Autowired
	UserServiceFilter userServiceFilter;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	public boolean login(String emailId , String password) {
		
		UserDetails userDetails = userServiceFilter.loadUserByUsername(emailId);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, 
				userDetails.getAuthorities());
		
		authenticationManager.authenticate(token);
		
		if(token.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(token);
			return true;
		}else {
			throw new BadCredentialsException(BAD_CRED_MESSAGE);
		}
		
	}
}
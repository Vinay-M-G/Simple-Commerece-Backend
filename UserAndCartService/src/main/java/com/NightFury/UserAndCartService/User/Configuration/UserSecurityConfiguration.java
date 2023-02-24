package com.NightFury.UserAndCartService.User.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class UserSecurityConfiguration {
	private static final String EP_CREATE_GUEST_USER = "/api/v1/user/createguestuser";
	private static final String EP_CREATE_REGISTERED_USER = "/api/v1/user/createregistereduser";
	private static final String EP_REGISTERED_USER_LOGIN = "/api/v1/user/reguserlogin";
	private static final String CART_BASE_URL = "/api/v1/carts/**";
	private static final String EP_GET_ADDRESS_RECOMMENDATION = "/api/v1/user/getaddresslist";
	private static final String EP_UPDATE_ADDRESS = "/api/v1/user/updateaddress";
	private static final String POST_ORDER = "api/v1/orders/createorder";
	
	@Value("${applicationSecurity.enableAuthorisationOnCartEndPoints}")
	private boolean isAuthEnabledForCart;
	
	@Bean
	public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder,
			UserDetailsService userDetailService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).build();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {	
		http.httpBasic();
		
		if(isAuthEnabledForCart) {
			http.authorizeRequests()
			.mvcMatchers(HttpMethod.POST, EP_CREATE_GUEST_USER, EP_CREATE_REGISTERED_USER, EP_REGISTERED_USER_LOGIN).permitAll()
			.mvcMatchers(HttpMethod.POST , CART_BASE_URL, EP_UPDATE_ADDRESS, POST_ORDER).authenticated()
			.mvcMatchers(HttpMethod.PATCH, CART_BASE_URL).authenticated()
			.mvcMatchers(HttpMethod.GET, CART_BASE_URL, EP_GET_ADDRESS_RECOMMENDATION).authenticated()
			.and().csrf().disable();
			
			return http.build();
		}
		
		http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
		
		return http.build();

	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
}

package com.NightFury.UserAndCartService.User.Configuration;


import java.util.Arrays;
import java.util.List;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class UserSecurityConfiguration {
	private static final String EP_CREATE_GUEST_USER = "/api/v1/user/createguestuser";
	private static final String EP_CREATE_REGISTERED_USER = "/api/v1/user/createregistereduser";
	private static final String EP_REGISTERED_USER_LOGIN = "/api/v1/user/reguserlogin";
	private static final String EP_ADD_PRODUCT_TO_BASKET = "/api/v1/carts/addtobasket";
	
	@Bean
	public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder,
			UserDetailsService userDetailService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).build();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {	
		http.httpBasic();
		
		/*
		 * Below commented code to be used once secure site is available
		 */
//		http.authorizeRequests()
//		.mvcMatchers(HttpMethod.POST, EP_CREATE_GUEST_USER, EP_CREATE_REGISTERED_USER, EP_REGISTERED_USER_LOGIN).permitAll()
//		.mvcMatchers(HttpMethod.POST , EP_ADD_PRODUCT_TO_BASKET).authenticated()
//		.and().csrf().disable();
		
		http.authorizeRequests()
		.mvcMatchers(HttpMethod.POST, EP_CREATE_GUEST_USER, EP_CREATE_REGISTERED_USER, EP_REGISTERED_USER_LOGIN).permitAll()
		.mvcMatchers(HttpMethod.POST , EP_ADD_PRODUCT_TO_BASKET).permitAll()
		.and().csrf().disable();
		
		return http.build();

	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
}

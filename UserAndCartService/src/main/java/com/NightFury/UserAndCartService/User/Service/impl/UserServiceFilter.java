package com.NightFury.UserAndCartService.User.Service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.NightFury.UserAndCartService.User.Entity.BaseUserModel;
import com.NightFury.UserAndCartService.User.Repository.BaseUserRepository;

@Service
public class UserServiceFilter implements UserDetailsService {
	
	public static final String ENCODED_ANONYMOUS_PASSWORD = "$2a$10$o0XMY3CvbgJrG3OPMsVoZOhWcaI.Kl4fSxmLoj3NpnOmCIe9HjEoO";
	public static final String EMAIL_NOT_FOUND_MESSAGE = "Email Id is not Registered";
	
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority("USER"));
        list.add(new SimpleGrantedAuthority("REGISTERED"));
        return list;
    }
    
	@Autowired
	BaseUserRepository baseUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
			
			List<BaseUserModel> user = baseUserRepository.getUsersBasedOnEmail(email);
			if(user.isEmpty()) {
				throw new UsernameNotFoundException(EMAIL_NOT_FOUND_MESSAGE);
			}
			return new org.springframework.security.core.userdetails.User(user.get(0).getEmail(), user.get(0).getSecretKey(), 
					getAuthorities());
		
	}

}
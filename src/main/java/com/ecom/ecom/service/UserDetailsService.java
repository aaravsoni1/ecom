package com.ecom.ecom.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {

//    UserDto createUser(UserDto dto);

    UserDetails loadUserByUsername(String name);

//    UserDto createUser(UserDto );
}

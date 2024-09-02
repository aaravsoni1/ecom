package com.ecom.ecom.service;

import com.ecom.ecom.payload.UserDto;
import org.springframework.security.core.userdetails.User;

public interface UserService {

    UserDto createUser(UserDto dto);

    User loadUserByUsername(String username);
}

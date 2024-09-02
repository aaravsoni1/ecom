package com.ecom.ecom.service;

import com.ecom.ecom.payload.SignupRequest;
import com.ecom.ecom.payload.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);
}

package com.ecom.ecom.service;

import com.ecom.ecom.payload.UserDto;
import com.ecom.ecom.repository.UserRepository;
import com.ecom.ecom.entity.User;
import org.springframework.stereotype.Service;

@Service

public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto dto) {
        User user=dtoToEntity(dto);
        User savedUser= userRepository.save(user);
        UserDto userDto=entityToDto(savedUser);
        return userDto;
    }

    UserDto entityToDto(User savedUser) {
        return null;
    }

    User dtoToEntity(UserDto dto) {
        return null;
    }
}

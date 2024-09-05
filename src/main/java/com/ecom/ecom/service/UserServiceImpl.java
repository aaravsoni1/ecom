package com.ecom.ecom.service;

import com.ecom.ecom.entity.User;
import com.ecom.ecom.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public UserDto createUser(UserDto dto) {
//        User user=dtoToEntity(dto);
//        User savedUser= userRepository.save(user);
//        UserDto userDto=entityToDto(savedUser);
//        return userDto;
//    }
//
//    UserDto entityToDto(User savedUser) {
//        return null;
//    }
//
//    User dtoToEntity(UserDto dto) {
//        return null;
//    }
//
//    org.springframework.security.core.userdetails.User

    @Override
    public org.springframework.security.core.userdetails.User loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<User> optionalUser = userRepository.findFirstByEmail(username);
        if(optionalUser.isEmpty()) throw new UsernameNotFoundException("Username not found", null);
        return new org.springframework.security.core.userdetails.User(optionalUser.get().getEmail(), optionalUser.get().getPassword(),
                new ArrayList<>());
    }
}

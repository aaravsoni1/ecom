//package com.ecom.ecom.controller;
//
//import com.ecom.ecom.repository.UserRepository;
//import com.ecom.ecom.payload.UserDto;
//import com.ecom.ecom.service.UserDetailsService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/user")
//public class UserController {
//
//    private UserRepository userRepository;
//
//    private UserDetailsService userService;
//
//    public UserController(UserRepository userRepository, UserDetailsService userService) {
//        this.userRepository = userRepository;
//        this.userService = userService;
//    }
////
////    @PostMapping("/createuser")
////    public ResponseEntity<?> createUser(@RequestBody UserDto dto) {
////        UserDto createdUser= userService.createUser(dto);
////        return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
////    }
//}

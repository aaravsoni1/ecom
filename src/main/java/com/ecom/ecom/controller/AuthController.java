package com.ecom.ecom.controller;


import com.ecom.ecom.entity.User;
import com.ecom.ecom.payload.AuthenticationRequest;
import com.ecom.ecom.payload.SignupRequest;
import com.ecom.ecom.payload.UserDto;
import com.ecom.ecom.repository.UserRepository;
import com.ecom.ecom.service.AuthService;
import com.ecom.ecom.service.UserService;
import com.ecom.ecom.utils.JwtUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";
    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final AuthService authService;


    @PostMapping("/authenticate")
    public void createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest,
            HttpServletResponse response) throws JSONException, IOException {
         try{
             authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername()
                     ,authenticationRequest.getPassword()));

         }catch (BadCredentialsException e) {
            throw new BadCredentialsException("incorrect username or password");
        }

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if(optionalUser.isPresent()) {
            response.getWriter().write(new JSONObject()
                    .put("userId",optionalUser.get().getId())
                            .put("role",optionalUser.get().getRole())
                            .toString()
                    );
            
            response.addHeader(HEADER_STRING,TOKEN_PREFIX + jwt);
        }

    }
     @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        if(authService.hasUserWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("user already exists", HttpStatus.BAD_REQUEST);
        }

        UserDto userDto = authService.createUser(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }

}

package com.ecom.ecom.payload;

import com.ecom.ecom.enums.UserRole;
import lombok.Data;

import javax.management.relation.Role;

@Data
public class SignupRequest {

//    private String firstName;
//
//    private String lastName;

    private String email;

    private String password;

    private String name;

//    private UserRole userRole;

}

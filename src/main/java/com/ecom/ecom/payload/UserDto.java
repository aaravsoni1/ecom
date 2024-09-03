package com.ecom.ecom.payload;

import com.ecom.ecom.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import javax.management.relation.Role;

@Data
public class UserDto {
    private long id;

    private String firstname;

    private String lastname;
    @NotNull
    @Email
    private String email;

    private String password;

    private UserRole userRole;

}

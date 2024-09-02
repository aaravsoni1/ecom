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
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    @Email
    private String email;
    @NotEmpty
    private String password;
    @NotNull
    private UserRole userRole;

}

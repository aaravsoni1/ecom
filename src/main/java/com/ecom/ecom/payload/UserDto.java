package com.ecom.ecom.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
    @NotEmpty
    private String phoneno;
    @NotNull
    private String role;
    @NotNull
    private String createdat;

}

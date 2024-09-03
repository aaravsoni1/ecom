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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @NotEmpty String getFirstname() {
        return firstname;
    }

    public void setFirstname(@NotEmpty String firstname) {
        this.firstname = firstname;
    }

    public @NotEmpty String getLastname() {
        return lastname;
    }

    public void setLastname(@NotEmpty String lastname) {
        this.lastname = lastname;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty String password) {
        this.password = password;
    }

    public @NotNull UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(@NotNull UserRole userRole) {
        this.userRole = userRole;
    }
}

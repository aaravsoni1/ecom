package com.ecom.ecom.payload;

import lombok.Data;

@Data
public class AuthenticationRequest {

   private String username;

//    private String email;
//
//    private String name;

    private String password;



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//


}

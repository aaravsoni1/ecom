package com.ecom.ecom.payload;

import lombok.Data;

@Data
public class LoginDto {
    private String type;
    private String token;
}

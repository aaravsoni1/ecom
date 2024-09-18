package com.ecom.ecom.payload;

import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    private String name;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private Long userId;
}

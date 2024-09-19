package com.ecom.ecom.service;

import com.ecom.ecom.payload.AddressDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface AddressService {
    AddressDto saveAddress(AddressDto addressDto, UserDetails userDetails);
    AddressDto getAddressById(Long id);
    AddressDto updateAddress(Long id, AddressDto addressDto);
    void deleteAddress(Long id);
}

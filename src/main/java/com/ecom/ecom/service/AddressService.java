package com.ecom.ecom.service;

import com.ecom.ecom.payload.AddressDto;

public interface AddressService {
    AddressDto saveAddress(AddressDto addressDto);
    AddressDto getAddressById(Long id);
    AddressDto updateAddress(Long id, AddressDto addressDto);
    void deleteAddress(Long id);
}

package com.ecom.ecom.service;

import com.ecom.ecom.payload.AddressDto;

import java.util.List;

public interface AddressService {
    AddressDto addAddress(AddressDto dto);
    AddressDto updateAddress(AddressDto dto);
    Boolean deleteAddress(Long addressId);
    AddressDto getAddressById(Long addressId);
    List<AddressDto> getAddressList();
}

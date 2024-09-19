package com.ecom.ecom.service;

import com.ecom.ecom.entity.Address;
import com.ecom.ecom.entity.User;
import com.ecom.ecom.payload.AddressDto;
import com.ecom.ecom.repository.AddressRepository;
import com.ecom.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AddressDto saveAddress(AddressDto addressDto, UserDetails userDetails) {
        Address address = convertToEntity(addressDto);
        Optional<User>opUser = userRepository.findFirstByEmail(userDetails.getUsername());
        if (opUser.isPresent()) {
            address.setUser(opUser.get());
        }
//        if(addressDto.getUserId() != null){
//            Optional<User> userOpt= userRepository.findById(addressDto.getUserId());
//
//            userOpt.ifPresent(address::setUser);
//        }
        Address savedAddress = addressRepository.save(address);
        return convertToDto(savedAddress);
    }

    @Override
    public AddressDto getAddressById(Long id) {
        Optional<Address> addressOpt = addressRepository.findById(id);
        return addressOpt.map(this::convertToDto).orElse(null);
    }

    @Override
    public AddressDto updateAddress(Long id, AddressDto addressDto) {
        Optional<Address> addressOpt = addressRepository.findById(id);
        if(addressOpt.isPresent()){
           Address existingAddress= addressOpt.get();
           existingAddress.setName(addressDto.getName());
           existingAddress.setStreet(addressDto.getStreet());
           existingAddress.setCity(addressDto.getCity());
           existingAddress.setState(addressDto.getState());
           existingAddress.setZipCode(addressDto.getZipCode());

           if(addressDto.getUserId() != null){
               Optional<User> userOpt= userRepository.findById(addressDto.getUserId());
               userOpt.ifPresent(existingAddress::setUser);
           }
           Address updatedAddress = addressRepository.save(existingAddress);
           return convertToDto(updatedAddress);
        }else{
            return null;
        }
    }

    @Override
    public void deleteAddress(Long id) {
        Optional<Address> addressOpt = addressRepository.findById(id);
        addressOpt.ifPresent(addressRepository::delete);
    }
    private AddressDto convertToDto(Address address){
        AddressDto dto = new AddressDto();
        dto.setId(address.getId());
        dto.setName(address.getName());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZipCode(address.getZipCode());

        if(address.getUser()!= null){
            dto.setUserId(address.getUser().getId());
        }
        return dto;
    }

    private Address convertToEntity(AddressDto addressDto){
        Address address = new Address();
        address.setName(addressDto.getName());
        address.setStreet(addressDto.getStreet());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setZipCode(addressDto.getZipCode());

        return address;
    }
}

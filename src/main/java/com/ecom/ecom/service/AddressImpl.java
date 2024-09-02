package com.ecom.ecom.service;

import com.ecom.ecom.entity.Address;
import com.ecom.ecom.payload.AddressDto;
import com.ecom.ecom.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressImpl implements AddressService{
    private AddressRepository addressRepository;

    public AddressImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public AddressDto addAddress(AddressDto dto) {
        Address entity = DtoToEntity(dto);
        Address saved = addressRepository.save(entity);
        return  EntityToDto(entity);
    }
    public Address DtoToEntity (AddressDto dto){
        Address entity = new Address();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setStreetAdress(dto.getStreetAdress());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setZipCode(dto.getZipCode());
        entity.setMobileNumber(dto.getMobileNumber());
        return entity;
    }
    public AddressDto EntityToDto(Address entity){
        AddressDto dto = new AddressDto();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setStreetAdress(entity.getStreetAdress());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setZipCode(entity.getZipCode());
        dto.setMobileNumber(entity.getMobileNumber());
        return dto;
    }

    @Override
    public AddressDto updateAddress(AddressDto dto) {
        Optional<Address> byId = addressRepository.findById(dto.getId());
        if (byId.isPresent()){
            Address entity = DtoToEntity(dto);
            Address saved = addressRepository.save(entity);
            return  EntityToDto(entity);
        }
        return null;
    }

    @Override
    public Boolean deleteAddress(Long addressId) {
        Optional<Address> byId = addressRepository.findById(addressId);
        if(byId.isPresent()){
            addressRepository.deleteById(addressId);
            return true;
        }
        return false;
    }

    @Override
    public AddressDto getAddressById(Long addressId) {
        Optional<Address> byId = addressRepository.findById(addressId);
        if(byId.isPresent()) {
            Address address = addressRepository.getById(addressId);
            return EntityToDto(address);
        }
        return null;
    }

    @Override
    public List<AddressDto> getAddressList() {
        List<Address> all = addressRepository.findAll();
        List<AddressDto> collect = all.stream().map(p -> EntityToDto(p)).collect(Collectors.toList());
        return collect;
    }
}

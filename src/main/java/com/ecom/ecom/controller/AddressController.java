package com.ecom.ecom.controller;

import com.ecom.ecom.payload.AddressDto;
import com.ecom.ecom.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @PostMapping("/add")
    public ResponseEntity<?> createAdress(@RequestBody AddressDto dto){
        AddressDto createdAddress = addressService.addAddress(dto);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateAddress(@RequestBody AddressDto dto){
        AddressDto updatedAddress = addressService.updateAddress(dto);
        if(updatedAddress!= null) {
            return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
        }
        return new ResponseEntity<>("Address Not Found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddress(@PathVariable Long id){
        AddressDto addressDto = addressService.getAddressById(id);
        if(addressDto!= null) {
            return new ResponseEntity<>(addressDto, HttpStatus.OK);
        }
        return new ResponseEntity<>("Address Not Found", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/allAddress")
    public ResponseEntity<?> getAllAddresses(){
        return new ResponseEntity<>(addressService.getAddressList(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id){
        boolean deleted = addressService.deleteAddress(id);
        if(deleted) {
            return new ResponseEntity<>("Address deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Address Not Found", HttpStatus.NOT_FOUND);
    }
}

package com.ecom.ecom.controller;

import com.ecom.ecom.payload.AddressDto;
import com.ecom.ecom.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<AddressDto> saveAddress(@Valid @RequestBody AddressDto addressDto){
        AddressDto createdAddress = addressService.saveAddress(addressDto);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddress(@PathVariable Long id){
        AddressDto addressDto = addressService.getAddressById(id);
        if(addressDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable Long id,
                                                    @Valid @RequestBody AddressDto addressDto){
        AddressDto updatedAddress = addressService.updateAddress(id, addressDto);
        if(updatedAddress == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

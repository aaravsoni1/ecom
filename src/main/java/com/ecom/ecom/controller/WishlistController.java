package com.ecom.ecom.controller;

import com.ecom.ecom.payload.WishlistDto;
import com.ecom.ecom.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/myWishlist")
    public ResponseEntity<?> getWishlist(WishlistDto dto){
        List<WishlistDto> all = wishlistService.getAllWishlists();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}

package com.ecom.ecom.controller;

import com.ecom.ecom.payload.WishlistDto;
import com.ecom.ecom.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/add")
    public ResponseEntity<WishlistDto> addProductToWishlist(
            @RequestParam Long userId,
            @RequestParam Long productId) {
        WishlistDto wishlistDto = wishlistService.addProductToWishlist(userId, productId);
        return new ResponseEntity<>(wishlistDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{wishlistId}")
    public ResponseEntity<String> removeProductFromWishlist(@PathVariable Long wishlistId) {
        boolean isDeleted = wishlistService.removeProductFromWishlist(wishlistId);
        if (isDeleted) {
            return new ResponseEntity<>("Product removed from wishlist", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Wishlist item not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WishlistDto>> getWishlistByUser(@PathVariable Long userId) {
        List<WishlistDto> wishlist = wishlistService.getWishlistByUser(userId);
        if (wishlist.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }
}

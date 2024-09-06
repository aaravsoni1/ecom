package com.ecom.ecom.controller;

import com.ecom.ecom.entity.Cart;
import com.ecom.ecom.payload.CartDTO;
import com.ecom.ecom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable Long userId) {
        CartDTO cartDTO = cartService.getCartByUserId(userId);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }


    @PostMapping("/{userId}/add")
    public ResponseEntity<CartDTO> addItemToCart(@PathVariable Long userId,
                                                 @RequestParam Long productId,
                                                 @RequestParam int quantity) {
        CartDTO cartDTO = cartService.addItemToCart(userId, productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }


    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<CartDTO> removeItemFromCart(@PathVariable Long userId,
                                                      @RequestParam Long productId) {
        CartDTO cartDTO = cartService.removeItemFromCart(userId, productId);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }


    @PutMapping("/{userId}/update")
    public ResponseEntity<CartDTO> updateItemQuantity(@PathVariable Long userId,
                                                      @RequestParam Long productId,
                                                      @RequestParam int quantity) {
        CartDTO cartDTO = cartService.updateItemQuantity(userId, productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }


    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

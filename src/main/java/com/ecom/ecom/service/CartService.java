package com.ecom.ecom.service;

import com.ecom.ecom.payload.CartDTO;

public interface CartService {


    CartDTO getCartByUserId(Long userId);

    CartDTO addItemToCart(Long userId, Long productId, int quantity);

    CartDTO removeItemFromCart(Long userId, Long productId);

    CartDTO updateItemQuantity(Long userId, Long productId, int quantity);

    void clearCart(Long userId);
}

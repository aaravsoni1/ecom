package com.ecom.ecom.service;

import com.ecom.ecom.payload.WishlistDto;

import java.util.List;

public interface WishlistService {


    WishlistDto addProductToWishlist(Long userId, Long productId);

    boolean removeProductFromWishlist(Long wishlistId);

    List<WishlistDto> getWishlistByUser(Long userId);
}

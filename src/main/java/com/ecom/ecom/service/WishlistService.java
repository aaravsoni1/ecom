package com.ecom.ecom.service;

import com.ecom.ecom.payload.WishlistDto;

import java.util.List;

public interface WishlistService {
    public WishlistDto addToWishlist(WishlistDto dto);
    public WishlistDto removeFromWishlist(WishlistDto dto);
    public WishlistDto undoFromWishlist(WishlistDto dto);
    public List<WishlistDto> getAllWishlists();
}

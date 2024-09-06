package com.ecom.ecom.service;

import com.ecom.ecom.payload.WishlistDto;
import com.ecom.ecom.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {
    @Autowired
    private WishListRepository wishListRepository;

    @Override
    public WishlistDto addToWishlist(WishlistDto dto) {

        return null;
    }

    @Override
    public WishlistDto removeFromWishlist(WishlistDto dto) {
        return null;
    }

    @Override
    public WishlistDto undoFromWishlist(WishlistDto dto) {
        return null;
    }

    @Override
    public List<WishlistDto> getAllWishlists() {
        return null;
    }
}

package com.ecom.ecom.service;

import com.ecom.ecom.entity.Product;
import com.ecom.ecom.entity.User;
import com.ecom.ecom.entity.WishList;
import com.ecom.ecom.payload.WishlistDto;
import com.ecom.ecom.repository.ProductRepository;
import com.ecom.ecom.repository.UserRepository;
import com.ecom.ecom.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final WishListRepository wishListRepository;

    public WishlistServiceImpl(UserRepository userRepository, ProductRepository productRepository, WishListRepository wishListRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.wishListRepository = wishListRepository;
    }


    @Override
    public WishlistDto addProductToWishlist(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Could not find User" + userId));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Could not find Product" + productId));

        WishList wishList = new WishList();
        wishList.setUser(user);
        wishList.setProduct(product);

        WishList savedWishlist = wishListRepository.save(wishList);
        return convertToDto(savedWishlist);
    }

    @Override
    public boolean removeProductFromWishlist(Long wishlistId) {
        if (wishListRepository.existsById(wishlistId)) {
            wishListRepository.deleteById(wishlistId);
            return true;
        }
        return false;
    }

    @Override
    public List<WishlistDto> getWishlistByUser(Long userId) {
        List<WishList> wishlist = wishListRepository.findByUserId(userId);
        return wishlist.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private WishlistDto convertToDto(WishList wishList) {
        WishlistDto dto = new WishlistDto();
        dto.setId(wishList.getId());
        dto.setUserId(wishList.getUser().getId());
        dto.setProductId(wishList.getProduct().getId());
        dto.setProductName(wishList.getProduct().getName());
        dto.setProductPrice(wishList.getProduct().getPrice());
        return dto;
    }

    private WishList convertToEntity(WishlistDto dto) {
        WishList wishlist = new WishList();
        wishlist.setId(dto.getId());

        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

        wishlist.setUser(user);
        wishlist.setProduct(product);
        return wishlist;
    }

}

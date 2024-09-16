package com.ecom.ecom.service;

import com.ecom.ecom.entity.Cart;
import com.ecom.ecom.entity.CartItem;
import com.ecom.ecom.entity.Product;
import com.ecom.ecom.payload.CartDTO;
import com.ecom.ecom.payload.CartItemDTO;
import com.ecom.ecom.repository.CartItemRepository;
import com.ecom.ecom.repository.CartRepository;
import com.ecom.ecom.repository.ProductRepository;
import com.ecom.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{


    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CartDTO getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
            return cartRepository.save(newCart);
        });
        return convertToDto(cart);
    }

    @Override
    public CartDTO addItemToCart(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found")));
            return cartRepository.save(newCart);
        });

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingCartItem.isPresent()) {
            // Update existing cart item
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(quantity);
            cartItem.setPrice(cartItem.getQuantity() * cartItem.getProduct().getPrice()); // Update price
            cartItemRepository.save(cartItem);
        } else {
            // Add new cart item
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(quantity * product.getPrice()); // Set price for the new item
            cart.getCartItems().add(cartItem);
            cartItemRepository.save(cartItem);
        }

        return convertToDto(cartRepository.save(cart));
    }


    @Override
    public CartDTO removeItemFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));

        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);

        return convertToDto(cart);
    }

    @Override
    public CartDTO updateItemQuantity(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));

        cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(cartItem -> {
                    cartItem.setQuantity(quantity);
                    cartItemRepository.save(cartItem);
                });

        return convertToDto(cartRepository.save(cart));
    }


    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }


    private CartDTO convertToDto(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUserId(cart.getUser().getId());
        cartDTO.setCartItems(cart.getCartItems().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
        return cartDTO;
    }


    private CartItemDTO convertToDto(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setProductId(cartItem.getProduct().getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setProductName(cartItem.getProduct().getName());
        cartItemDTO.setProductPrice(cartItem.getProduct().getPrice());
        return cartItemDTO;
    }


}

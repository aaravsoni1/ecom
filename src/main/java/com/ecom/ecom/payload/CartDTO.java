package com.ecom.ecom.payload;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {


    private Long id;

    private Long userId;

    private List<CartItemDTO> cartItems;



}


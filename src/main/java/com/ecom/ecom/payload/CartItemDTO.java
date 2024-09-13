package com.ecom.ecom.payload;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long id;
    private Long productId;
    private int quantity;
    private String productName;
    private double productPrice;
}

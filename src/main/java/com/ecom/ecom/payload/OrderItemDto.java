package com.ecom.ecom.payload;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long orderItemId;
    private Long productId;
    private String productName;
    private Integer productPrice;
    private int quantity;
    private int totalPrice;
}

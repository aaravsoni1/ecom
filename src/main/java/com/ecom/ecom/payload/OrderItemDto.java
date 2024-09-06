package com.ecom.ecom.payload;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private Integer quantity;
    private Integer price;
}

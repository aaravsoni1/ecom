package com.ecom.ecom.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Data
public class OrderDto {
    private Long orderId;
    private Long userId;
    private String orderDate;
    private String status;
    private List<OrderItemDto> orderItems;

}

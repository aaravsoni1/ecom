package com.ecom.ecom.service;

import com.ecom.ecom.entity.User;
import com.ecom.ecom.payload.OrderDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface OrderService {

    OrderDto placeOrder(OrderDto orderDto,  UserDetails userDetails);



    OrderDto getOrderById(Long orderId);
    List<OrderDto> getOrdersByUser(Long userId);
}

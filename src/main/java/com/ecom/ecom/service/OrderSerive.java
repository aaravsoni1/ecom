package com.ecom.ecom.service;

import com.ecom.ecom.payload.OrderDto;

import java.util.List;

public interface OrderSerive {
    public OrderDto createOrder(OrderDto order);
    public OrderDto getOrderById(Long orderId);
    public OrderDto updateOrder(OrderDto order);
    public List<OrderDto> getAllOrder(Long userId);
}

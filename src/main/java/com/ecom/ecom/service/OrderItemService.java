package com.ecom.ecom.service;

import com.ecom.ecom.payload.OrderItemDto;
import org.springframework.stereotype.Service;

@Service
public interface OrderItemService {
    public OrderItemDto addProduct(OrderItemDto product);
}

package com.ecom.ecom.service;

import com.ecom.ecom.entity.Order;
import com.ecom.ecom.exception.ResourceNotFoundException;
import com.ecom.ecom.payload.OrderDto;
import com.ecom.ecom.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderSerive{


    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDto createOrder(OrderDto order) {
        Order entity = DtoToEntity(order);
        entity.setCreatedAt(new Date());
        OrderDto orderDto = EntityToDto(entity);
        orderRepository.save(entity);
        return orderDto;
    }
    @Override
    public List<OrderDto> getAllOrder(Long userId) {
        List<Order> all = orderRepository.findAll();
        List<OrderDto> orders = all.stream().map(o -> EntityToDto(o)).collect(Collectors.toList());
        return orders;
    }

    public OrderDto EntityToDto(Order entity) {
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setTotal_price(entity.getTotal_price());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public Order DtoToEntity(OrderDto order) {
        Order entity = new Order();
        entity.setId(order.getId());
        entity.setStatus(order.getStatus());
        entity.setTotal_price(order.getTotal_price());
        return entity;
    }

    @Override
    public OrderDto getOrderById(Long orderId) throws ResourceNotFoundException {
        Order entity = orderRepository.findById(orderId).orElse(null);
        if (entity!= null) {
            return EntityToDto(entity);
        }
        return null;
    }

    @Override
    public OrderDto updateOrder(OrderDto order) {
        Order entity = orderRepository.findById(order.getId()).orElse(null);
        if (entity!= null) {
            entity.setStatus(order.getStatus());
            entity.setTotal_price(order.getTotal_price());
            entity.setUpdatedAt(new Date());
            Order saved = orderRepository.save(entity);
            return EntityToDto(saved);
        }
        return null;
    }
}

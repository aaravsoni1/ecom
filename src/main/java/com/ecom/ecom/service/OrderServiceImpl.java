package com.ecom.ecom.service;

import com.ecom.ecom.entity.Order;
import com.ecom.ecom.entity.OrderItem;
import com.ecom.ecom.entity.Product;
import com.ecom.ecom.entity.User;
import com.ecom.ecom.payload.OrderDto;
import com.ecom.ecom.payload.OrderItemDto;
import com.ecom.ecom.repository.OrderRepository;
import com.ecom.ecom.repository.ProductRepository;
import com.ecom.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }



    @Override
    public OrderDto placeOrder(OrderDto orderDto, UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findFirstByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(orderDto.getOrderDate());
        order.setStatus(orderDto.getStatus());

        List<OrderItem> orderItems = orderDto.getOrderItems().stream()
                .map(orderItemDto -> convertToEntity(orderItemDto, order))
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        return convertToDto(savedOrder);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDto(order);
    }

    @Override
    public List<OrderDto> getOrdersByUser(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Convert Order entity to DTO
    private OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setOrderId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());

        List<OrderItemDto> orderItems = order.getOrderItems().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        dto.setOrderItems(orderItems);

        return dto;
    }

    // Convert OrderItem entity to DTO
    private OrderItemDto convertToDto(OrderItem orderItem) {
        OrderItemDto dto = new OrderItemDto();
        dto.setOrderItemId(orderItem.getId());
        dto.setProductId(orderItem.getProduct().getId());
        dto.setProductName(orderItem.getProduct().getName());
        dto.setProductPrice((orderItem.getProduct().getPrice()));
        dto.setTotalPrice((orderItem.getProduct().getPrice()) * (orderItem.getQuantity()));
        dto.setQuantity(orderItem.getQuantity());
        return dto;
    }

    // Convert DTO to OrderItem entity
    private OrderItem convertToEntity(OrderItemDto dto, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setQuantity(dto.getQuantity());

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        orderItem.setProduct(product);

        return orderItem;
    }

}

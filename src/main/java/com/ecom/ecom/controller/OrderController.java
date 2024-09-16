package com.ecom.ecom.controller;

import com.ecom.ecom.entity.User;
import com.ecom.ecom.payload.OrderDto;
import com.ecom.ecom.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public ResponseEntity<OrderDto> placeOrder(@AuthenticationPrincipal UserDetails userDetails, @RequestBody OrderDto orderDto) {
        OrderDto placedOrder = orderService.placeOrder(orderDto,userDetails);
        return new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUser(@PathVariable Long userId) {
        List<OrderDto> orders = orderService.getOrdersByUser(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}

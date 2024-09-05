package com.ecom.ecom.controller;

import com.ecom.ecom.payload.OrderDto;
import com.ecom.ecom.service.OrderSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/orders")
public class OrderController {
    @Autowired
    private OrderSerive orderSerive;

    @PostMapping("/addedOrder")
    public ResponseEntity<?> addOrder(@RequestBody OrderDto dto){
        OrderDto order = orderSerive.createOrder(dto);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/myOrders")
    public ResponseEntity<?> getAllOrdersForUser(@RequestParam Long userId){
        return new ResponseEntity<>(orderSerive.getAllOrder(userId), HttpStatus.OK);
    }

    @PutMapping("/editOrder")
    public ResponseEntity<?> editOrder(@RequestBody OrderDto dto) {
        OrderDto updatedOrder = orderSerive.updateOrder(dto);
        if (updatedOrder == null) {
            return new ResponseEntity<>("Order Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
}

package com.ecom.ecom.controller;

import com.ecom.ecom.exception.OrderNotFoundException;
import com.ecom.ecom.payload.PaymentDTO;
import com.ecom.ecom.repository.OrderRepository;
import com.ecom.ecom.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/process")
    public ResponseEntity<?> processPayment(@RequestBody PaymentDTO paymentDTO, @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
      try {
          PaymentDTO processedPayment = paymentService.processPayment(paymentDTO, userDetails);


          return new ResponseEntity<>(processedPayment, HttpStatus.OK);
      }catch (OrderNotFoundException ex){
          Map<String, String> response = new HashMap<>();
          response.put("status", "404");
          response.put("error", ex.getMessage());
          return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
      }catch (Exception ex) {
          // Handle any other generic exceptions
          Map<String, String> response = new HashMap<>();
          response.put("status", "500");
          response.put("error", "An error occurred: " + ex.getMessage());
          return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }


    }
}

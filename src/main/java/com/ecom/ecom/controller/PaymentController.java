package com.ecom.ecom.controller;

import com.ecom.ecom.payload.PaymentDTO;
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



@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentDTO> processPayment(@RequestBody PaymentDTO paymentDTO, @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();

        PaymentDTO processedPayment = paymentService.processPayment(paymentDTO, userDetails);

        return new ResponseEntity<>(processedPayment, HttpStatus.OK);
    }
}

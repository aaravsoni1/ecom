package com.ecom.ecom.service;

import com.ecom.ecom.payload.PaymentDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface PaymentService {


    PaymentDTO processPayment(PaymentDTO paymentDTO, UserDetails userDetails);
}

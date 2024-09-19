package com.ecom.ecom.service;

import com.ecom.ecom.entity.Order;
import com.ecom.ecom.entity.Payment;
import com.ecom.ecom.entity.User;
import com.ecom.ecom.payload.PaymentDTO;
import com.ecom.ecom.repository.OrderRepository;
import com.ecom.ecom.repository.PaymentRepository;
import com.ecom.ecom.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey; // Initialize Stripe API Key
    }

    @Override
    public PaymentDTO processPayment(PaymentDTO paymentDTO, UserDetails userDetails) {

        Payment payment = convertToEntity(paymentDTO, userDetails);


        Charge charge = createStripeCharge(paymentDTO);


        if (charge.getPaid()) {
            payment.setPaymentStatus("Success");
        } else {
            payment.setPaymentStatus("Failed");
        }


        Payment savedPayment = paymentRepository.save(payment);


        return convertToDTO(savedPayment);
    }

    private Payment convertToEntity(PaymentDTO paymentDTO, UserDetails userDetails) {
        Payment payment = new Payment();

        User user = userRepository.findByEmail(userDetails.getUsername());
        payment.setUser(user);


        Order order = orderRepository.findById(paymentDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        payment.setOrder(order);

        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentStatus("Pending");
        payment.setPaymentDate(LocalDateTime.now());
        return payment;
    }

    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setOrderId(payment.getOrder().getId());
        paymentDTO.setPaymentMethod(payment.getPaymentMethod());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setPaymentStatus(payment.getPaymentStatus());
        paymentDTO.setPaymentDate(payment.getPaymentDate());
        return paymentDTO;
    }

    private Charge createStripeCharge(PaymentDTO paymentDTO) {
        try {
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", (int) (paymentDTO.getAmount() * 100)); // Stripe uses cents
            chargeParams.put("currency", "usd");
            chargeParams.put("description", "Order #" + paymentDTO.getOrderId());
            chargeParams.put("source", "tok_visa"); // For testing purposes, use Stripe's test token

            return Charge.create(chargeParams);
        } catch (StripeException e) {
            throw new RuntimeException("Payment processing failed", e);
        }
    }

}

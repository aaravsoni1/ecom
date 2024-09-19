package com.ecom.ecom.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PaymentDTO {
    private Long orderId;
    private String paymentStatus;
    private String paymentMethod;
    private Double amount;
}

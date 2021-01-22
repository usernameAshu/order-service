package com.poc.orderservice.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    private int paymentId;
    private String transactionId;
    private String paymentStatus;
    private int orderId;
    private double orderAmount;
}

package com.poc.orderservice.common;

import com.poc.orderservice.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Order order;
    private double paymentAmount;
    private String transactionID;
    private String status;
}

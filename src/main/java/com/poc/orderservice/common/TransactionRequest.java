package com.poc.orderservice.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poc.orderservice.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @JsonProperty("order")
    private Order order;
    @JsonProperty("payment")
    private PaymentDTO payment;
}

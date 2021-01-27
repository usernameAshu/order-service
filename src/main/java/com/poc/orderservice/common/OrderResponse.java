package com.poc.orderservice.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class OrderResponse {
    @JsonProperty("order_id")
    private int orderId;

    @JsonProperty("order_name")
    private String orderName;

    @JsonProperty("order_quantity")
    private int orderQuantity;

    @JsonProperty("order_price")
    private double orderPrice;
}

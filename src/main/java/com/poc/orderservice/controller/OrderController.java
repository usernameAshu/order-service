package com.poc.orderservice.controller;

import com.poc.orderservice.common.TransactionRequest;
import com.poc.orderservice.common.TransactionResponse;
import com.poc.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/v1")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse addOrder(@RequestBody TransactionRequest request) {
        return service.saveOrder(request);
    }
}
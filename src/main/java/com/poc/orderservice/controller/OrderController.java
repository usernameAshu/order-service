package com.poc.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poc.orderservice.common.OrderResponse;
import com.poc.orderservice.common.TransactionRequest;
import com.poc.orderservice.common.TransactionResponse;
import com.poc.orderservice.entity.Order;
import com.poc.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders/v1")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse addOrder(@RequestBody TransactionRequest request) throws JsonProcessingException {
        return service.saveOrder(request);
    }

    @GetMapping
    public OrderResponse getOrder(@RequestParam(value = "orderId") int orderId) throws JsonProcessingException {
        return service.getOrder(orderId);
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() throws JsonProcessingException {
        return service.getAllOrders();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse updateOrder(@RequestBody TransactionRequest request, @RequestParam(value = "orderId") int orderId)
            throws JsonProcessingException {
        return service.updateOrder(request, orderId);
    }
}

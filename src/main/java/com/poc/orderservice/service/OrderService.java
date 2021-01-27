package com.poc.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poc.orderservice.common.OrderResponse;
import com.poc.orderservice.common.TransactionRequest;
import com.poc.orderservice.common.TransactionResponse;
import com.poc.orderservice.entity.Order;

import java.util.List;

public interface OrderService {

    TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException;

    OrderResponse getOrder(int orderId) throws JsonProcessingException;

    List<Order> getAllOrders() throws JsonProcessingException;

    TransactionResponse updateOrder(TransactionRequest request, int orderId) throws JsonProcessingException;
}

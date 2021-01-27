package com.poc.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poc.orderservice.common.TransactionRequest;
import com.poc.orderservice.common.TransactionResponse;
import com.poc.orderservice.entity.Order;

public interface OrderService {

    TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException;
}

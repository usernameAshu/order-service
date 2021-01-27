package com.poc.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.orderservice.common.PaymentDTO;
import com.poc.orderservice.common.TransactionRequest;
import com.poc.orderservice.common.TransactionResponse;
import com.poc.orderservice.entity.Order;
import com.poc.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class OrderServiceImpl implements OrderService {

    private Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderRepository repository;

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String ENDPOINT_URL;

    @Override
    public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {
        Order order = request.getOrder();
        PaymentDTO payment = request.getPayment();
        payment.setOrderAmount(order.getOrderPrice());
        Order savedOrder = repository.save(order);
        payment.setOrderId(savedOrder.getOrderId());
        LOGGER.info("order-service Request:saveOrder : {}", new ObjectMapper().writeValueAsString(request));
        //rest api call to payment-service
        PaymentDTO response = restTemplate.postForObject(ENDPOINT_URL, payment, PaymentDTO.class);
        LOGGER.info("payment-service:saveOrder response : {}", new ObjectMapper().writeValueAsString(response));
        return new TransactionResponse(savedOrder, response.getOrderAmount(), response.getTransactionId(),
                response.getPaymentStatus());
    }
}

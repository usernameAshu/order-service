package com.poc.orderservice.service;

import com.poc.orderservice.common.PaymentDTO;
import com.poc.orderservice.common.TransactionRequest;
import com.poc.orderservice.common.TransactionResponse;
import com.poc.orderservice.entity.Order;
import com.poc.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private final String ENDPOINT_URL = "http://PAYMENT-SERVICE/payments/v1";

    @Override
    public TransactionResponse saveOrder(TransactionRequest request) {
        Order order = request.getOrder();
        PaymentDTO payment = request.getPayment();
        payment.setOrderAmount(order.getOrderPrice());
        Order savedOrder = repository.save(order);
        payment.setOrderId(savedOrder.getOrderId());
        //rest api call to payment-service
        PaymentDTO response = restTemplate.postForObject(ENDPOINT_URL, payment, PaymentDTO.class);

        return new TransactionResponse(savedOrder, response.getOrderAmount(), response.getTransactionId(),
                response.getPaymentStatus());
    }
}

package com.poc.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.orderservice.common.OrderResponse;
import com.poc.orderservice.common.PaymentDTO;
import com.poc.orderservice.common.TransactionRequest;
import com.poc.orderservice.common.TransactionResponse;
import com.poc.orderservice.entity.Order;
import com.poc.orderservice.exception.OrderNotFoundException;
import com.poc.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Autowired
    OrderResponse orderResponse;

    @Override
    public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {
        Order order = request.getOrder();
        PaymentDTO payment = request.getPayment();
        payment.setOrderAmount(order.getOrderPrice());
        Order savedOrder = repository.save(order);
        payment.setOrderId(savedOrder.getOrderId());
        LOGGER.info("order-service:saveOrder() Request : {}", new ObjectMapper().writeValueAsString(request));
        //rest api call to payment-service
        PaymentDTO response = restTemplate.postForObject(ENDPOINT_URL, payment, PaymentDTO.class);
        LOGGER.info("payment-service:saveOrder() Response : {}", new ObjectMapper().writeValueAsString(response));
        return new TransactionResponse(savedOrder, response.getOrderAmount(), response.getTransactionId(),
                response.getPaymentStatus());
    }

    @Override
    public OrderResponse getOrder(int orderId) throws JsonProcessingException {
        LOGGER.info("order-service:getOrder() Request : {}", new ObjectMapper().writeValueAsString(orderId));
        Order allByOrderId = repository
                .findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("No order exists for order id:" + orderId));
        BeanUtils.copyProperties(allByOrderId, orderResponse);
        LOGGER.info("order-service:getOrder() Response : {}", new ObjectMapper().writeValueAsString(orderResponse));
        return orderResponse;
    }

    @Override
    public List<Order> getAllOrders() throws JsonProcessingException {
        LOGGER.info("order-service:getAllOrders() Request : {}",
                new ObjectMapper().writeValueAsString("Get All Orders"));
        List<Order> orderList = repository.findAll();
        LOGGER.info("order-service:getAllOrders() Response : {}",
                new ObjectMapper().writeValueAsString("Array of size returned:" + orderList.size()));
        return orderList;
    }

    @Override
    public TransactionResponse updateOrder(TransactionRequest request, int orderId) throws JsonProcessingException {

        if (repository
                .findByOrderId(orderId)
                .isPresent()) {
            LOGGER.info("order-service:updateOrder() Response : {Updating the order}");
            Order order = repository.findByOrderId(orderId).get();
            order.setOrderName(request.getOrder().getOrderName());
            order.setOrderPrice(request.getOrder().getOrderPrice());
            order.setOrderQuantity(request.getOrder().getOrderQuantity());
            repository.save(order);
            return new TransactionResponse(order,order.getOrderPrice(),null,null);
        } else
            throw new OrderNotFoundException("Order is not created, please create an order");

    }
}

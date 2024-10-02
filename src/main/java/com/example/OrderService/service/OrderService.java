package com.example.OrderService.service;


import com.example.OrderService.entity.Order;
import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {


    OrderRepository orderRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    public OrderService(OrderRepository orderRepository)
    {
        this.orderRepository=orderRepository;
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
        ResponseEntity<Product> product = restTemplate.exchange("http://localhost:8080/products/"+order.getProduct_id(), HttpMethod.GET,null,Product.class);
        double orderPrice=order.getQuantity()*(product.getBody().getPrice());
        order.setTotal_price(orderPrice);
        return orderRepository.save(order);
    }
}

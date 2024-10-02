package com.example.OrderService.controller;


import com.example.OrderService.entity.Order;
import com.example.OrderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    OrderService orderService;

    public OrderController()
    {

    }
    @Autowired
    public OrderController(OrderService orderService)
    {
        this.orderService=orderService;
    }

    @GetMapping
    public List<Order> getAllProducts()
    {

        return orderService.getAllOrders();
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order)
    {

        return orderService.createOrder(order);
    }
}

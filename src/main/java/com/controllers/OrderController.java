package com.controllers;

import com.models.Order;
import com.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return this.orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable UUID id) {
        return this.orderService.getOrder(id);
    }

    @PostMapping("/create")
    public Order createOrder(@RequestBody Order order) {
        return this.orderService.createOrder(order);
    }
}

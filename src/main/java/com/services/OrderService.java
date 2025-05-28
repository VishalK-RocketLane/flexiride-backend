package com.services;


import com.models.Order;
import com.repos.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    public Order getOrder(UUID id) {
        Optional<Order> order = this.orderRepository.findById(id);
        return order.orElse(null);
    }
}

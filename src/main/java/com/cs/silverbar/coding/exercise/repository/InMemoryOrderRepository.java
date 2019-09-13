package com.cs.silverbar.coding.exercise.repository;

import com.cs.silverbar.coding.exercise.domain.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class InMemoryOrderRepository implements OrderRepository {

    private final Map<String, Order> orders = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
        orders.put(order.getId(), order);
        log.info("Created order with id={}", order.getId());
        return order;
    }

    @Override
    public void delete(String orderId) {
        orders.remove(orderId);
        log.info("deleted order with id={}", orderId);
    }

    @Override
    public Optional<Order> findById(String orderId) {
        log.info("Finding order by id={}", orderId);
        return Optional.ofNullable(orders.get(orderId));
    }

    @Override
    public List<Order> findAll() {
        log.info("Fetching all orders");
        return new ArrayList<>(orders.values());
    }

    @Override
    public void deleteAll() {
        log.info("Fetching all orders");
        orders.clear();
    }
}

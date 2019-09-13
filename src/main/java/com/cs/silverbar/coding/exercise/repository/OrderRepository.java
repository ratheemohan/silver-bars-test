package com.cs.silverbar.coding.exercise.repository;

import com.cs.silverbar.coding.exercise.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(final Order order);

    void delete(final String orderId);

    Optional<Order> findById(String orderId);

    List<Order> findAll();

    void deleteAll();
}

package com.cs.silverbar.coding.exercise.repository;

import com.cs.silverbar.coding.exercise.domain.Order;

import java.util.List;
import java.util.Optional;

/**
 * Provides api for order management.
 */
public interface OrderRepository {

    /**
     * Save's the give order.
     * @param order
     * @return saved order
     */
    Order save(final Order order);

    /**
     * Removes order based on the Id.
     * @param orderId
     */
    void delete(final String orderId);

    /**
     * find order by orderId.
     * @param orderId
     * @return returns order if found, else Optional.empty()
     */
    Optional<Order> findById(String orderId);

    /**
     * Returns all the orders.
     * @return list of orders
     */
    List<Order> findAll();

    /**
     * deletes all the orders. Primary objective of this method is for testing.
     */
    void deleteAll();
}

package com.cs.silverbar.coding.exercise.service;

import com.cs.silverbar.coding.exercise.domain.Order;
import com.cs.silverbar.coding.exercise.domain.OrderType;
import com.cs.silverbar.coding.exercise.exception.OrderNotFoundException;
import com.cs.silverbar.coding.exercise.repository.OrderRepository;
import com.cs.silverbar.coding.exercise.util.IdGenerator;
import com.cs.silverbar.coding.exercise.util.TimeSource;
import com.cs.silverbar.coding.exercise.web.model.LiveOrderSummary;
import com.cs.silverbar.coding.exercise.web.model.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@AllArgsConstructor
@Slf4j
public class DefaultOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final IdGenerator idGenerator;
    private final TimeSource timeSource;

    @Override
    public Order create(final OrderRequest orderRequest) {
        final Order order = transform(orderRequest);
        orderRepository.save(order);
        log.info("Creating order={}", order);
        return order;
    }

    @Override
    public void cancel(final String orderId) {
        if (orderRepository.findById(orderId).isEmpty()) {
            throw new OrderNotFoundException(orderId);
        }
        orderRepository.delete(orderId);
        log.info("Cancelled order with id={}", orderId);
    }

    @Override
    public LiveOrderSummary calculateOrderSummaryByOrderType(final OrderType orderType) {
        log.info("Getting live order summary for orderType={}", orderType);

        final List<Order> ordersByType = orderRepository.findAll();

        return new OrderSummaryService(ordersByType).calculateSummaryFor(orderType);
    }


    private Order transform(OrderRequest orderRequest) {
        return Order.builder()
                .id(idGenerator.generateId())
                .orderType(orderRequest.getOrderType())
                .price(orderRequest.getPrice())
                .quantity(orderRequest.getQuantity())
                .userId(orderRequest.getUserId())
                .createdAt(timeSource.now())
                .build();
    }
}

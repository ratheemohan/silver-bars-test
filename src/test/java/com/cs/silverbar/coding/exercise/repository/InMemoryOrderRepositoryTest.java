package com.cs.silverbar.coding.exercise.repository;


import com.cs.silverbar.coding.exercise.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cs.silverbar.coding.exercise.fixtures.Fixtures.anOrder;
import static org.assertj.core.api.Assertions.assertThat;

class InMemoryOrderRepositoryTest {

    private final OrderRepository orderRepository = new InMemoryOrderRepository();

    @BeforeEach
    private void cleanUp(){
        orderRepository.deleteAll();
    }

    @Test
    public void shouldCreateOrder() {
        Order order = anOrder("1");

        assertThat(orderRepository.findAll().size()).isEqualTo(0);
        orderRepository.save(order);

        assertThat(orderRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void shouldDeleteOrder() {
        Order order = anOrder(UUID.randomUUID().toString());
        orderRepository.save(order);

        assertThat(orderRepository.findAll().size()).isEqualTo(1);

        orderRepository.delete(order.getId());

        assertThat(orderRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void shouldReturnOptionalEmptyWhenOrderDoesNotExist() {
        Optional<Order> order = orderRepository.findById("NON_EXISTENT_ID");

        assertThat(order.isPresent()).isFalse();
    }

    @Test
    public void shouldReturnAllOrders() {
        final Order order_1 = anOrder(UUID.randomUUID().toString());
        final Order order_2 = anOrder(UUID.randomUUID().toString());
        orderRepository.save(order_1);
        orderRepository.save(order_2);
        List<Order> allOrders = orderRepository.findAll();

        assertThat(allOrders.size()).isEqualTo(2);
        assertThat(allOrders).containsExactlyInAnyOrder(order_1, order_2);
    }
}
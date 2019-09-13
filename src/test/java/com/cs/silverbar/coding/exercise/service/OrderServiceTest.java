package com.cs.silverbar.coding.exercise.service;


import com.cs.silverbar.coding.exercise.domain.Order;
import com.cs.silverbar.coding.exercise.exception.OrderNotFoundException;
import com.cs.silverbar.coding.exercise.repository.OrderRepository;
import com.cs.silverbar.coding.exercise.util.IdGenerator;
import com.cs.silverbar.coding.exercise.util.TimeSource;
import com.cs.silverbar.coding.exercise.web.model.LiveOrderSummary;
import com.cs.silverbar.coding.exercise.web.model.OrderRequest;
import com.cs.silverbar.coding.exercise.web.model.OrderSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.cs.silverbar.coding.exercise.domain.OrderType.BUY;
import static com.cs.silverbar.coding.exercise.domain.OrderType.SELL;
import static com.cs.silverbar.coding.exercise.fixtures.Fixtures.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderRepository orderRepository;
    private IdGenerator idGenerator;
    private TimeSource timeSource;
    private OrderService orderService;

    private final Instant NOW = Instant.now();
    private final String ORDER_ID = "1";

    @BeforeEach
    public void setUp() {
        orderRepository = mock(OrderRepository.class);
        idGenerator = mock(IdGenerator.class);
        timeSource = mock(TimeSource.class);
        orderService = new DefaultOrderService(orderRepository, idGenerator, timeSource);

        when(timeSource.now()).thenReturn(NOW);
        when(idGenerator.generateId()).thenReturn(ORDER_ID);
    }

    @Test
    public void shouldCreateOrder() {
        when(orderRepository.save(any())).thenReturn(anOrder(ORDER_ID));

        final OrderRequest request = orderRequest(SELL);
        final Order createdOrder = orderService.create(request);

        assertThat(createdOrder.getId()).isEqualTo(ORDER_ID);
        assertThat(createdOrder.getOrderType()).isEqualTo(SELL);
        assertThat(createdOrder.getPrice()).isEqualTo(OfPrice("100"));
        assertThat(createdOrder.getQuantity()).isEqualTo(ofQuantity("1.0"));
        assertThat(createdOrder.getCreatedAt()).isEqualTo(NOW);
        assertThat(createdOrder.getUserId()).isEqualTo(request.getUserId());

        verify(orderRepository, times(1)).save(any());
        verify(timeSource, times(1)).now();
        verify(idGenerator, times(1)).generateId();
    }

    @Test
    public void shouldCancelOrder() {
        when(orderRepository.findById(anyString())).thenReturn(Optional.of(anOrder("1")));
        doNothing().when(orderRepository).delete(anyString());
        orderService.cancel("1");

        verify(orderRepository, times(1)).delete(anyString());
    }

    @Test
    public void shouldThrowOrderNotFoundExceptionWhenTryingToDeleteNonExistentOrder() {
        when(orderRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThatExceptionOfType(OrderNotFoundException.class).isThrownBy(() -> orderService.cancel("1"));
    }

    @Test
    public void shouldCreateLiveOrderSummaryForSellOrders() {
        when(orderRepository.findAll()).thenReturn(asList(
                buildOrderFor("1", "user1", ofQuantity("3.5"), OfPrice("306"), SELL),
                buildOrderFor("2", "user2", ofQuantity("1.2"), OfPrice("310"), SELL),
                buildOrderFor("3", "user3", ofQuantity("1.5"), OfPrice("307"), SELL),
                buildOrderFor("4", "user4", ofQuantity("2.0"), OfPrice("306"), SELL)));

        final LiveOrderSummary liveOrderSummary = orderService.calculateOrderSummaryByOrderType(SELL);
        final List<OrderSummary> orderSummary = liveOrderSummary.getSummary();

        assertThat(liveOrderSummary.getSummary().size()).isEqualTo(3);
        assertThat(orderSummary.get(0)).isEqualTo(new OrderSummary(OfPrice("306"), ofQuantity("5.5")));
        assertThat(orderSummary.get(1)).isEqualTo(new OrderSummary(OfPrice("307"), ofQuantity("1.5")));
        assertThat(orderSummary.get(2)).isEqualTo(new OrderSummary(OfPrice("310"), ofQuantity("1.2")));
    }

    @Test
    public void shouldCreateLiveOrderSummaryForBuyOrders() {
        when(orderRepository.findAll()).thenReturn(asList(
                buildOrderFor("1", "user1", ofQuantity("3.5"), OfPrice("306.50"), BUY),
                buildOrderFor("2", "user2", ofQuantity("2.2"), OfPrice("310"), BUY),
                buildOrderFor("3", "user3", ofQuantity("2.5"), OfPrice("307"), BUY),
                buildOrderFor("4", "user4", ofQuantity("3.0"), OfPrice("306.50"), BUY),
                buildOrderFor("5", "user5", ofQuantity("3.5"), OfPrice("306"), BUY),
                buildOrderFor("6", "user6", ofQuantity("8.5"), OfPrice("307"), BUY)));

        final LiveOrderSummary liveOrderSummary = orderService.calculateOrderSummaryByOrderType(BUY);
        final List<OrderSummary> orderSummary = liveOrderSummary.getSummary();

        assertThat(liveOrderSummary.getSummary().size()).isEqualTo(4);
        assertThat(orderSummary.get(0)).isEqualTo(new OrderSummary(OfPrice("310"), ofQuantity("2.2")));
        assertThat(orderSummary.get(1)).isEqualTo(new OrderSummary(OfPrice("307"), ofQuantity("11.0")));
        assertThat(orderSummary.get(2)).isEqualTo(new OrderSummary(OfPrice("306.50"), ofQuantity("6.5")));
        assertThat(orderSummary.get(3)).isEqualTo(new OrderSummary(OfPrice("306"), ofQuantity("3.5")));
    }

}

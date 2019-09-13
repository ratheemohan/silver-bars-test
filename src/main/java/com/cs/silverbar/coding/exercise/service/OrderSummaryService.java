package com.cs.silverbar.coding.exercise.service;

import com.cs.silverbar.coding.exercise.domain.Order;
import com.cs.silverbar.coding.exercise.domain.OrderType;
import com.cs.silverbar.coding.exercise.web.model.LiveOrderSummary;
import com.cs.silverbar.coding.exercise.web.model.OrderSummary;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.cs.silverbar.coding.exercise.domain.OrderType.SELL;

@Value
@Slf4j
public final class OrderSummaryService {

    @NonNull
    private final List<Order> orders;

    LiveOrderSummary calculateSummaryFor(OrderType orderType) {
        log.debug("Calculating {} summary from {} orders", orderType.name(), orders.size());
        Map<BigDecimal, BigDecimal> priceQuantityAggregation = aggregateQuantitiesByPrice(orderType);
        List<OrderSummary> summaryItems = createSortedSummaryItems(orderType, priceQuantityAggregation);
        log.debug("Summarised into {} items", summaryItems.size());
        return new LiveOrderSummary(summaryItems);
    }

    private Map<BigDecimal, BigDecimal> aggregateQuantitiesByPrice(OrderType orderType) {
        return orders.stream()
                .filter(order -> order.getOrderType() == orderType)
                .collect(Collectors.groupingBy(
                        Order::getPrice,
                        sumUpQuantities()));
    }

    private Collector<Order, ?, BigDecimal> sumUpQuantities() {
        return Collectors.reducing(
                BigDecimal.ZERO,
                Order::getQuantity,
                BigDecimal::add);
    }

    private List<OrderSummary> createSortedSummaryItems(OrderType orderType, Map<BigDecimal, BigDecimal> priceQuantityAggregation) {
        return priceQuantityAggregation.entrySet().stream()
                .map(item -> new OrderSummary(item.getKey(), item.getValue()))
                .sorted(sortByPrice(orderType))
                .collect(Collectors.toList());
    }

    private Comparator<OrderSummary> sortByPrice(OrderType orderType) {
        return orderType == SELL
                ? Comparator.comparing(OrderSummary::getPrice)
                : Comparator.comparing(OrderSummary::getPrice).reversed();
    }
}

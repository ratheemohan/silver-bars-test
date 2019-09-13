package com.cs.silverbar.coding.exercise.fixtures;

import com.cs.silverbar.coding.exercise.domain.Order;
import com.cs.silverbar.coding.exercise.domain.OrderType;
import com.cs.silverbar.coding.exercise.web.model.OrderRequest;

import java.math.BigDecimal;
import java.time.Instant;

import static com.cs.silverbar.coding.exercise.domain.OrderType.SELL;

public class Fixtures {

    public static Order anOrder(String id) {
        return Order.builder()
                .userId("user")
                .quantity(ofQuantity("1.0"))
                .price(OfPrice("100"))
                .orderType(SELL)
                .id(id)
                .createdAt(Instant.now())
                .build();
    }

    public static BigDecimal ofQuantity(String quantity) {
        return new BigDecimal(quantity);
    }

    public static BigDecimal OfPrice(String price) {
        return new BigDecimal(price);
    }

    public static OrderRequest orderRequest(final OrderType orderType) {
        return new OrderRequest("UserId", ofQuantity("1.0"), OfPrice("100"), orderType);
    }

    public static Order buildOrderFor(String id, String userId, BigDecimal quantity, BigDecimal price, OrderType orderType) {
        return Order.builder()
                .userId(userId)
                .quantity(quantity)
                .price(price)
                .orderType(orderType)
                .id(id)
                .createdAt(Instant.now())
                .build();
    }
}

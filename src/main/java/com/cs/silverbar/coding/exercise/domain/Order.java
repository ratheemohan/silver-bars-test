package com.cs.silverbar.coding.exercise.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * An Immutable class to represent Order
 */
@Value
@Builder
public class Order {

    @NonNull
    private final String id;

    @NonNull
    private final String userId;

    @NonNull
    private final BigDecimal quantity;

    @NonNull
    private final BigDecimal price;

    @NonNull
    private final OrderType orderType;

    @NonNull
    private final Instant createdAt;

}

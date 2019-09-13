package com.cs.silverbar.coding.exercise.web.model;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

/**
 * Order summary per merged order.
 */
@Data
public class OrderSummary {
    @NonNull
    private final BigDecimal price;
    @NonNull
    private final BigDecimal quantity;
}

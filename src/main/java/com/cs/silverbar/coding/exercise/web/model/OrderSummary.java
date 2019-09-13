package com.cs.silverbar.coding.exercise.web.model;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class OrderSummary {
    @NonNull
    private final BigDecimal price;
    @NonNull
    private final BigDecimal quantity;
}

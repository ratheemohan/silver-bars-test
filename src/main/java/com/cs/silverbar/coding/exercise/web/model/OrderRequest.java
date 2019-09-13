package com.cs.silverbar.coding.exercise.web.model;

import com.cs.silverbar.coding.exercise.domain.OrderType;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class OrderRequest {

    @NotEmpty
    @NonNull
    private String userId;

    @NonNull
    @Min(0)
    private BigDecimal quantity;

    @NonNull
    @Min(0)
    private BigDecimal price;

    @NonNull
    private OrderType orderType;
}


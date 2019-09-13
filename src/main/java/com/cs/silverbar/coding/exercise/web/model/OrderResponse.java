package com.cs.silverbar.coding.exercise.web.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

/**
 * Response for successful order creation
 */
@Data
public class OrderResponse {
    @NonNull
    @NotEmpty
    private String orderId;
}

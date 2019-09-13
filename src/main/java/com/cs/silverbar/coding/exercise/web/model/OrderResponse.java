package com.cs.silverbar.coding.exercise.web.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderResponse {
    @NonNull
    @NotEmpty
    private String orderId;
}

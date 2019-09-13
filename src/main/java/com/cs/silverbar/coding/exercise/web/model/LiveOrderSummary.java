package com.cs.silverbar.coding.exercise.web.model;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

/**
 * Api model to provide live order summary
 */
@Data
public class LiveOrderSummary {

    @NonNull
    private List<OrderSummary> summary;

}

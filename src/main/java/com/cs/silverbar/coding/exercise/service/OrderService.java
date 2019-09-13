package com.cs.silverbar.coding.exercise.service;

import com.cs.silverbar.coding.exercise.domain.Order;
import com.cs.silverbar.coding.exercise.domain.OrderType;
import com.cs.silverbar.coding.exercise.web.model.LiveOrderSummary;
import com.cs.silverbar.coding.exercise.web.model.OrderRequest;

public interface OrderService {

    Order create(OrderRequest orderRequest);

    void cancel(String orderId);

    LiveOrderSummary calculateOrderSummaryByOrderType(final OrderType orderType);

}

package com.cs.silverbar.coding.exercise.service;

import com.cs.silverbar.coding.exercise.domain.Order;
import com.cs.silverbar.coding.exercise.domain.OrderType;
import com.cs.silverbar.coding.exercise.exception.OrderNotFoundException;
import com.cs.silverbar.coding.exercise.web.model.LiveOrderSummary;
import com.cs.silverbar.coding.exercise.web.model.OrderRequest;

import static com.cs.silverbar.coding.exercise.domain.OrderType.*;

/**
 * Service for order management
 */
public interface OrderService {

    /**
     * Transform {@link OrderRequest} to {@link Order} and create order
     * @param orderRequest
     * @return created order
     */
    Order create(OrderRequest orderRequest);

    /**
     * cancel order by orderId.
     *
     * @param orderId
     * @throws OrderNotFoundException, when order not found for given orderId
     */
    void cancel(String orderId) throws OrderNotFoundException;

    /**
     * Calculates order summary by orderId. The order's with the same price are merged.
     * The Sell order summary will be sorted on price from lowest to highest,
     * where as Buy orders will be vice-versa
     *
     * @param orderType
     * @return LiveOrderSummary
     */
    LiveOrderSummary calculateOrderSummaryByOrderType(final OrderType orderType);

}

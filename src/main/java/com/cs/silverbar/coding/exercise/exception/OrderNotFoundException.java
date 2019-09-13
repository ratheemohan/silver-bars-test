package com.cs.silverbar.coding.exercise.exception;

/**
 * Thrown when Order By Id doesn't exist in the system
 */
public class OrderNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Order with id=%s doesn't exist";

    public OrderNotFoundException(String orderId) {
        super(String.format(ERROR_MESSAGE, orderId));
    }
}

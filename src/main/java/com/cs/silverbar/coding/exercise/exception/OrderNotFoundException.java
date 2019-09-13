package com.cs.silverbar.coding.exercise.exception;

public class OrderNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Order with id=%s doesn't exist";

    public OrderNotFoundException(String orderId) {
        super(String.format(ERROR_MESSAGE, orderId));
    }
}

package com.cs.silverbar.coding.exercise.web.controller;

import com.cs.silverbar.coding.exercise.domain.Order;
import com.cs.silverbar.coding.exercise.service.OrderService;
import com.cs.silverbar.coding.exercise.web.model.LiveOrderSummary;
import com.cs.silverbar.coding.exercise.web.model.OrderRequest;
import com.cs.silverbar.coding.exercise.web.model.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cs.silverbar.coding.exercise.domain.OrderType.BUY;
import static com.cs.silverbar.coding.exercise.domain.OrderType.SELL;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public OrderResponse registerOrder(@Valid @RequestBody final OrderRequest request) {
        log.info("Received new order request={}", request);
        Order created = orderService.create(request);
        return new OrderResponse(created.getId());
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(NO_CONTENT)
    public void cancelOrder(@PathVariable("orderId") final String orderId) {
        log.info("Received request to cancel order with id={}", orderId);
        orderService.cancel(orderId);
    }

    @GetMapping(value = "/summary/sell", produces = APPLICATION_JSON_UTF8_VALUE)
    public LiveOrderSummary sellOrderSummary() {
        log.info("Calculating sell orders live summary");
        return orderService.calculateOrderSummaryByOrderType(SELL);
    }

    @GetMapping(value = "/summary/buy", produces = APPLICATION_JSON_UTF8_VALUE)
    public LiveOrderSummary buyOrderSummary() {
        log.info("Calculating buy orders live summary");
        return orderService.calculateOrderSummaryByOrderType(BUY);
    }
}

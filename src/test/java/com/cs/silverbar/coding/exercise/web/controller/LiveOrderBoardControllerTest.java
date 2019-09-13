package com.cs.silverbar.coding.exercise.web.controller;

import com.cs.silverbar.coding.exercise.repository.OrderRepository;
import com.cs.silverbar.coding.exercise.web.model.LiveOrderSummary;
import com.cs.silverbar.coding.exercise.web.model.OrderRequest;
import com.cs.silverbar.coding.exercise.web.model.OrderResponse;
import com.cs.silverbar.coding.exercise.web.model.OrderSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.cs.silverbar.coding.exercise.domain.OrderType.BUY;
import static com.cs.silverbar.coding.exercise.domain.OrderType.SELL;
import static com.cs.silverbar.coding.exercise.fixtures.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LiveOrderBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void cleanUp(){
        orderRepository.deleteAll();
    }

    @Test
    public void shouldCreateOrder() throws Exception {
        final OrderResponse orderResponse = createOrder(orderRequest(SELL));

        assertThat(orderResponse).isNotNull();
    }

    @Test
    public void shouldCancelExistingOrder() throws Exception {

        final OrderResponse createdOrder = createOrder(orderRequest(SELL));

        final MvcResult mvcResult = mockMvc.perform(
                delete("/orders/{orderId}", createdOrder.getOrderId())
                        .contentType(APPLICATION_JSON_UTF8_VALUE)).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(NO_CONTENT.value());
    }

    @Test
    public void shouldReturn404WhenCancellingNonExistentOrder() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(
                delete("/orders/{orderId}", "1")
                        .contentType(APPLICATION_JSON_UTF8_VALUE)).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(NOT_FOUND.value());
    }

    @Test
    public void shouldReturnSellOrderSummary() throws Exception {
        createOrder(new OrderRequest("user1", ofQuantity("3.5"), OfPrice("306"), SELL));
        createOrder(new OrderRequest("user2", ofQuantity("1.2"), OfPrice("310"), SELL));
        createOrder(new OrderRequest("user3", ofQuantity("1.5"), OfPrice("307"), SELL));
        createOrder(new OrderRequest("user4", ofQuantity("2.0"), OfPrice("306"), SELL));

        final MvcResult mvcResult = mockMvc.perform(
                get("/orders/summary/sell")
                        .contentType(APPLICATION_JSON_UTF8_VALUE)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        final LiveOrderSummary liveOrderSummary = objectMapper.readValue(response.getContentAsString(), LiveOrderSummary.class);

        assertThat(response.getStatus()).isEqualTo(OK.value());
        assertThat(liveOrderSummary.getSummary().size()).isEqualTo(3);
        assertThat(liveOrderSummary.getSummary().get(0)).isEqualTo(new OrderSummary(OfPrice("306"), ofQuantity("5.5")));
        assertThat(liveOrderSummary.getSummary().get(1)).isEqualTo(new OrderSummary(OfPrice("307"), ofQuantity("1.5")));
        assertThat(liveOrderSummary.getSummary().get(2)).isEqualTo(new OrderSummary(OfPrice("310"), ofQuantity("1.2")));
    }

    @Test
    public void shouldReturnBuyOrderSummary() throws Exception {
        createOrder(new OrderRequest("user1", ofQuantity("1.0"), OfPrice("305"), BUY));
        createOrder(new OrderRequest("user2", ofQuantity("1.5"), OfPrice("306.5"), BUY));
        createOrder(new OrderRequest("user3", ofQuantity("2.0"), OfPrice("307"), BUY));
        createOrder(new OrderRequest("user4", ofQuantity("2.5"), OfPrice("308.5"), BUY));
        createOrder(new OrderRequest("user5", ofQuantity("3.0"), OfPrice("306.5"), BUY));
        createOrder(new OrderRequest("user6", ofQuantity("3.5"), OfPrice("308.5"), BUY));

        final MvcResult mvcResult = mockMvc.perform(
                get("/orders/summary/buy")
                        .contentType(APPLICATION_JSON_UTF8_VALUE)).andReturn();

        final MockHttpServletResponse response = mvcResult.getResponse();
        final LiveOrderSummary liveOrderSummary = objectMapper.readValue(response.getContentAsString(), LiveOrderSummary.class);

        assertThat(response.getStatus()).isEqualTo(OK.value());
        assertThat(liveOrderSummary.getSummary().size()).isEqualTo(4);
        assertThat(liveOrderSummary.getSummary().get(0)).isEqualTo(new OrderSummary(OfPrice("308.5"), ofQuantity("6.0")));
        assertThat(liveOrderSummary.getSummary().get(1)).isEqualTo(new OrderSummary(OfPrice("307"), ofQuantity("2.0")));
        assertThat(liveOrderSummary.getSummary().get(2)).isEqualTo(new OrderSummary(OfPrice("306.5"), ofQuantity("4.5")));
        assertThat(liveOrderSummary.getSummary().get(3)).isEqualTo(new OrderSummary(OfPrice("305"), ofQuantity("1.0")));
    }

    private OrderResponse createOrder(OrderRequest request) throws Exception {
        final MvcResult result = mockMvc.perform(
                post("/orders")
                        .contentType(APPLICATION_JSON_UTF8_VALUE)
                        .content(objectMapper.writeValueAsString(request))).andReturn();

        final MockHttpServletResponse response = result.getResponse();
        final OrderResponse orderResponse = objectMapper.readValue(response.getContentAsString(), OrderResponse.class);

        assertThat(response.getStatus()).isEqualTo(OK.value());
        return orderResponse;
    }
}

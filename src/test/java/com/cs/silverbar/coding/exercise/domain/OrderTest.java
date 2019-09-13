package com.cs.silverbar.coding.exercise.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.cs.silverbar.coding.exercise.domain.OrderType.SELL;
import static com.cs.silverbar.coding.exercise.fixtures.Fixtures.anOrder;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

    @Test
    public void shouldTestOrder(){
        final Order order = anOrder("1");

        assertThat(order.getUserId()).isEqualTo("user");
        assertThat(order.getId()).isEqualTo("1");
        assertThat(order.getCreatedAt()).isNotNull();
        assertThat(order.getQuantity()).isEqualTo(new BigDecimal("1.0"));
        assertThat(order.getPrice()).isEqualTo(new BigDecimal("100"));
        assertThat(order.getOrderType()).isEqualTo(SELL);
    }

}

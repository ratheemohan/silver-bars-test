package com.cs.silverbar.coding.exercise.config;

import com.cs.silverbar.coding.exercise.SilverBarApplication;
import com.cs.silverbar.coding.exercise.repository.InMemoryOrderRepository;
import com.cs.silverbar.coding.exercise.repository.OrderRepository;
import com.cs.silverbar.coding.exercise.service.DefaultOrderService;
import com.cs.silverbar.coding.exercise.service.OrderService;
import com.cs.silverbar.coding.exercise.util.DefaultTimeSource;
import com.cs.silverbar.coding.exercise.util.IdGenerator;
import com.cs.silverbar.coding.exercise.util.TimeSource;
import com.cs.silverbar.coding.exercise.util.UUIDGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures {@link Bean}'s required for {@link SilverBarApplication}
 */
@Configuration
public class ApplicationConfiguration {

    @Bean
    public IdGenerator idGenerator() {
        return new UUIDGenerator();
    }

    @Bean
    public TimeSource timeSource() {
        return new DefaultTimeSource();
    }

    @Bean
    public OrderRepository orderRepository() {
        return new InMemoryOrderRepository();
    }

    @Bean
    public OrderService orderService(OrderRepository orderRepository, IdGenerator idGenerator, TimeSource timeSource) {
        return new DefaultOrderService(orderRepository, idGenerator, timeSource);
    }

}

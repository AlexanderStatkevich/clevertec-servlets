package com.statkevich.receipttask.service.singletonfactories;

import com.statkevich.receipttask.service.OrderService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceSingletonTest {

    @Test
    void getInstance() {
        OrderService instanceFirst = OrderServiceSingleton.getInstance();
        OrderService instanceSecond = OrderServiceSingleton.getInstance();
        assertThat(instanceFirst).isEqualTo(instanceSecond);
    }
}

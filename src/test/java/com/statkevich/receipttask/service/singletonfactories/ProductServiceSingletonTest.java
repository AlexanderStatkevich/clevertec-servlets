package com.statkevich.receipttask.service.singletonfactories;

import com.statkevich.receipttask.service.ProductService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceSingletonTest {

    @Test
    void getInstance() {
        ProductService instanceFirst = ProductServiceSingleton.getInstance();
        ProductService instanceSecond = ProductServiceSingleton.getInstance();
        assertThat(instanceFirst).isEqualTo(instanceSecond);
    }
}

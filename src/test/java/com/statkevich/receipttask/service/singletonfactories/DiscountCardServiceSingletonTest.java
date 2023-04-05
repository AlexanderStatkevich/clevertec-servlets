package com.statkevich.receipttask.service.singletonfactories;

import com.statkevich.receipttask.service.DiscountCardService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountCardServiceSingletonTest {

    @Test
    void checkThatFactoryReturnTheSameInstance() {
        DiscountCardService instanceFirst = DiscountCardServiceSingleton.getInstance();
        DiscountCardService instanceSecond = DiscountCardServiceSingleton.getInstance();
        assertThat(instanceFirst).isEqualTo(instanceSecond);
    }
}

package com.statkevich.receipttask.service.singletonfactories;

import com.statkevich.receipttask.service.OrderService;

public class OrderServiceSingleton {
    private volatile static OrderService INSTANCE;

    private OrderServiceSingleton() {
    }

    public static OrderService getInstance() {
        if (INSTANCE == null) {
            synchronized (OrderServiceSingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OrderService(DiscountCardServiceSingleton.getInstance());
                }
            }
        }
        return INSTANCE;
    }
}

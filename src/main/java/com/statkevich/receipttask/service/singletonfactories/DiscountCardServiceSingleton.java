package com.statkevich.receipttask.service.singletonfactories;

import com.statkevich.receipttask.dao.singletonfactories.DiscountCardDaoSingleton;
import com.statkevich.receipttask.service.DiscountCardService;

public class DiscountCardServiceSingleton {
    private volatile static DiscountCardService INSTANCE;

    private DiscountCardServiceSingleton() {
    }

    public static DiscountCardService getInstance() {
        if (INSTANCE == null) {
            synchronized (DiscountCardServiceSingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DiscountCardService(DiscountCardDaoSingleton.getINSTANCE());
                }
            }
        }
        return INSTANCE;
    }
}

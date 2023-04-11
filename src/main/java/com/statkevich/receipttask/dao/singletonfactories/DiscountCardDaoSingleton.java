package com.statkevich.receipttask.dao.singletonfactories;

import com.statkevich.receipttask.dao.api.DiscountCardDao;
import com.statkevich.receipttask.dao.sql.DiscountCardDaoImpl;
import com.statkevich.receipttask.util.DataSourceHolder;

public class DiscountCardDaoSingleton {
    private volatile static DiscountCardDao INSTANCE;

    private DiscountCardDaoSingleton() {
    }

    public static DiscountCardDao getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (DiscountCardDaoSingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DiscountCardDaoImpl(DataSourceHolder.getDataSource());
                }
            }
        }
        return INSTANCE;
    }
}

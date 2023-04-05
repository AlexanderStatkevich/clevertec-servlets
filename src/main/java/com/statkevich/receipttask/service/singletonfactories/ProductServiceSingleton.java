package com.statkevich.receipttask.service.singletonfactories;

import com.statkevich.receipttask.dao.singletonfactories.ProductDaoSingleton;
import com.statkevich.receipttask.service.ProductService;

public class ProductServiceSingleton {
    private volatile static ProductService INSTANCE;

    private ProductServiceSingleton() {
    }

    public static ProductService getInstance() {
        if (INSTANCE == null) {
            synchronized (ProductServiceSingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ProductService(ProductDaoSingleton.getINSTANCE());
                }
            }
        }
        return INSTANCE;
    }
}

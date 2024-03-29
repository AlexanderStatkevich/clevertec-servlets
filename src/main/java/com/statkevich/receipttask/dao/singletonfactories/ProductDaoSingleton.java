package com.statkevich.receipttask.dao.singletonfactories;

import com.statkevich.receipttask.dao.api.ProductDao;
import com.statkevich.receipttask.dao.sql.ProductDaoImpl;
import com.statkevich.receipttask.util.DataSourceHolder;

public class ProductDaoSingleton {

    private volatile static ProductDao INSTANCE;

    private ProductDaoSingleton() {
    }

    public static ProductDao getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (ProductDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ProductDaoImpl(DataSourceHolder.getDataSource());
                }
            }
        }
        return INSTANCE;
    }
}

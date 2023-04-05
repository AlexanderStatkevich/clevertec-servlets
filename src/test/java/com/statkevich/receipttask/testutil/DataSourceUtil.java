package com.statkevich.receipttask.testutil;

import org.apache.commons.dbcp2.BasicDataSource;
import org.testcontainers.containers.JdbcDatabaseContainer;

import javax.sql.DataSource;

public final class DataSourceUtil {

    private DataSourceUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static DataSource getDataSource(JdbcDatabaseContainer<?> container) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername(container.getUsername());
        basicDataSource.setPassword(container.getPassword());
        basicDataSource.setDriverClassName(container.getDriverClassName());
        basicDataSource.setUrl(container.getJdbcUrl());

        return basicDataSource;
    }
}

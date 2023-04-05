package com.statkevich.receipttask.util;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceHolder {

    public static DataSource getDataSource() {
        Properties properties = getProperties();
        BasicDataSource basicDataSource = new BasicDataSource();
        String username = properties.getProperty("dataSource.username");
        String password = properties.getProperty("dataSource.password");
        String drivenClassName = properties.getProperty("dataSource.drivenClassName");
        String url = properties.getProperty("dataSource.url");
        String maxIdle = properties.getProperty("dataSource.maxIdle");
        String maxTotal = properties.getProperty("dataSource.maxTotal");
        String maxWaitMillis = properties.getProperty("dataSource.maxWaitMillis");

        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setDriverClassName(drivenClassName);
        basicDataSource.setUrl(url);
        basicDataSource.setMaxIdle(Integer.parseInt(maxIdle));
        basicDataSource.setMaxTotal(Integer.parseInt(maxTotal));
        basicDataSource.setMaxWaitMillis(Long.parseLong(maxWaitMillis));

        return basicDataSource;
    }

    private static Properties getProperties() {
        try (InputStream input = DataSourceHolder.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
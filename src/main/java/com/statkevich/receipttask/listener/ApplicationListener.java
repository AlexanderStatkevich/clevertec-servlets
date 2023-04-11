package com.statkevich.receipttask.listener;


import com.statkevich.receipttask.dao.sql.NativeInitDao;
import com.statkevich.receipttask.util.DataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.InputStream;
import java.util.Map;

@Slf4j
@WebListener
public class ApplicationListener implements ServletContextListener {

    public static final String OPTION_INIT_DB = "create";
    public static final String OPTION_SKIP_INIT_DB = "skip";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Yaml yaml = new Yaml();
        InputStream inputStream = ApplicationListener.class
                .getClassLoader()
                .getResourceAsStream("application.yml");
        Map<String, Object> propertyMap = yaml.load(inputStream);
        String initialize_db = (String) propertyMap.get("initialize_db");
        switch (initialize_db) {
            case OPTION_INIT_DB -> new NativeInitDao(DataSourceHolder.getDataSource()).initDB();
            case OPTION_SKIP_INIT_DB -> log.info("Skip initialization DB");
            default -> throw new IllegalStateException("Incorrect init value");
        }
        ;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}

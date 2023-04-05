package com.statkevich.receipttask.cache;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CacheHolder {
    public static final String LRU = "LRU";
    public static final String LFU = "LFU";
    private static final Map<String, Cache> CACHE_MAP = new ConcurrentHashMap<>();
    private static final CacheFactory factory = initCacheFactory();

    private CacheHolder() {
    }

    /**
     * @param name passing to get cache from storage
     * @return corresponding cache from storage
     */
    public static Cache get(String name) {
        Cache cache = CACHE_MAP.get(name);
        if (null == cache) {
            cache = factory.createCache();
            CACHE_MAP.put(name, cache);
        }
        return cache;
    }

    private static CacheFactory initCacheFactory() {
        Yaml yaml = new Yaml();
        InputStream inputStream = CacheHolder.class
                .getClassLoader()
                .getResourceAsStream("application.yml");
        Map<String, Object> propertyMap = yaml.load(inputStream);
        LinkedHashMap cache = (LinkedHashMap) propertyMap.get("cache");
        String type = (String) cache.get("type");
        Integer size = (Integer) cache.get("size");
        return switch (type) {
            case LFU -> new LfuCacheFactory(size);
            case LRU -> new LruCacheFactory(size);
            default -> throw new IllegalStateException("Algorithm doesn't supported");
        };
    }
}

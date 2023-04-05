package com.statkevich.receipttask.cache;

public class LruCacheFactory implements CacheFactory {

    private final int capacity;

    public LruCacheFactory(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public Cache createCache() {
        return new LruCache(capacity);
    }
}

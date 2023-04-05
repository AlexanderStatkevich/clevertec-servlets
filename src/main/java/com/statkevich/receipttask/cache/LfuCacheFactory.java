package com.statkevich.receipttask.cache;

public class LfuCacheFactory implements CacheFactory {

    private final int capacity;

    public LfuCacheFactory(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public Cache createCache() {

        return new LfuCache(capacity);
    }
}

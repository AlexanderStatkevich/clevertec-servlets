package com.statkevich.receipttask.cache;

/**
 * Factory class is engaged into creating Cache implementations.
 * Depending on factory type, the implementation of the cache is selected.
 */
public interface CacheFactory {
    Cache createCache();
}

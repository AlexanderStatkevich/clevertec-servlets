package com.statkevich.receipttask.cache;

public interface Cache {
    /**
     * Insert an object in cache
     */
    void put(Object key, Object value);

    /**
     * Retrieve an object from cache
     */
    Object get(Object key);

    /**
     * Delete an object from cache
     */
    void evict(Object key);
}

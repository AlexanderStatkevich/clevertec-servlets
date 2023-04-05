package com.statkevich.receipttask.cache;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Implementation of least recently used cache algorithm.
 *
 * @see com.statkevich.receipttask.cache.LfuCache
 */
public class LruCache implements Cache {
    private final Deque<Object> cacheObjectList = new LinkedList<>();
    private final Map<Object, Node> pointerMap = new HashMap<>();
    private final int CACHE_CAPACITY;

    public LruCache(int capacity) {
        this.CACHE_CAPACITY = capacity;
    }

    @Override
    public void put(Object key, Object value) {
        if (pointerMap.containsKey(key)) {
            Node currentNode = pointerMap.get(key);
            cacheObjectList.remove(currentNode.key);
        } else {
            if (cacheObjectList.size() == CACHE_CAPACITY) {
                Object temp = cacheObjectList.removeLast();
                pointerMap.remove(temp);
            }
        }
        Node newItem = new Node(key, value);
        cacheObjectList.addFirst(newItem.key);
        pointerMap.put(key, newItem);
    }

    @Override
    public Object get(Object key) {
        if (pointerMap.containsKey(key)) {
            Node current = pointerMap.get(key);
            cacheObjectList.remove(current.key);
            cacheObjectList.addFirst(current.key);
            return current.value;
        }
        return null;
    }

    @Override
    public void evict(Object key) {
        pointerMap.remove(key);
    }

    private record Node(Object key, Object value) {
    }
}

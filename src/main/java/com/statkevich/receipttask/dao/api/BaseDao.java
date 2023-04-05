package com.statkevich.receipttask.dao.api;


import java.util.List;

public interface BaseDao<E, K> {

    void save(E entity);

    E get(K key);

    List<E> getByKeys(List<K> keys);

    E update(E entity);

    void delete(K key);
}

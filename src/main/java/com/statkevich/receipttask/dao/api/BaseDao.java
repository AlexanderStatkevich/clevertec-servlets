package com.statkevich.receipttask.dao.api;


import com.statkevich.receipttask.dto.PageDto;

import java.util.List;

public interface BaseDao<E, K> {

    void save(E entity);

    E get(K key);

    List<E> getByKeys(List<K> keys);

    PageDto<E> getPage(Long pageNumber, Long pageSize);

    E update(E entity);

    void delete(K key);
}

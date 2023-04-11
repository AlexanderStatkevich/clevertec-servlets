package com.statkevich.receipttask.util;

import com.statkevich.receipttask.domain.mapper.EntityMapper;
import com.statkevich.receipttask.dto.PageDto;

import java.util.List;

public class PageMapper<E, D> {

    private final EntityMapper<E, D> entityMapper;

    public PageMapper(EntityMapper<E, D> entityMapper) {
        this.entityMapper = entityMapper;
    }

    public PageDto<D> mapToDto(PageDto<E> page) {
        long number = page.getNumber();
        long size = page.getSize();
        long totalPages = page.getTotalPages();
        boolean first = page.isFirst();
        long numberOfElements = page.getNumberOfElements();
        boolean last = page.isLast();
        List<E> entityList = page.getContent();
        List<D> dtoList = entityList.stream()
                .map(entityMapper::toDto)
                .toList();

        return PageDto.<D>builder()
                .number(number)
                .size(size)
                .totalPages(totalPages)
                .first(first)
                .numberOfElements(numberOfElements)
                .last(last)
                .content(dtoList)
                .build();
    }
}

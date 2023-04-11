package com.statkevich.receipttask.domain.mapper;

public interface EntityMapper<E, D> {
    D toDto(E source);
}

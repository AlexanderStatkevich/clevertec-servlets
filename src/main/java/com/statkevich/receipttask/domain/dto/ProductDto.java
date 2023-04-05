package com.statkevich.receipttask.domain.dto;

import com.statkevich.receipttask.domain.SaleType;

import java.math.BigDecimal;
import java.util.Set;

public record ProductDto(
        Long id,
        String name,
        BigDecimal price,
        Set<SaleType> saleTypes
) {
}

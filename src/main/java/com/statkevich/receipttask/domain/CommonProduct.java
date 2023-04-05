package com.statkevich.receipttask.domain;

import com.statkevich.receipttask.annotation.CacheKey;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Getter
@RequiredArgsConstructor
@Builder
public class CommonProduct {
    @CacheKey
    private final Long id;
    private final String name;
    private final BigDecimal price;
    private final Set<SaleType> saleTypes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonProduct product = (CommonProduct) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(saleTypes, product.saleTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, saleTypes);
    }
}

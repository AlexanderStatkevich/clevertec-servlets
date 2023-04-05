package com.statkevich.receipttask.testutil.model;

import com.statkevich.receipttask.domain.CommonProduct;
import com.statkevich.receipttask.domain.SaleType;
import com.statkevich.receipttask.testutil.api.Builder;

import java.math.BigDecimal;
import java.util.Set;

public class CommonProductTestBuilder implements Builder<CommonProduct> {
    private Long id = 1L;
    private String name = "NAME";
    private BigDecimal price = BigDecimal.valueOf(5.0);
    private Set<SaleType> saleTypes = Set.of(SaleType.TEN_PERCENT_OFF_FOR_MORE_THAN_FIVE_PRODUCTS);

    private CommonProductTestBuilder() {
    }

    private CommonProductTestBuilder(CommonProductTestBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.saleTypes = builder.saleTypes;
    }

    public static CommonProductTestBuilder aProduct() {
        return new CommonProductTestBuilder();
    }

    public CommonProductTestBuilder withId(Long id) {
        final var copy = new CommonProductTestBuilder(this);
        copy.id = id;
        return copy;
    }

    public CommonProductTestBuilder withName(String name) {
        final var copy = new CommonProductTestBuilder(this);
        copy.name = name;
        return copy;
    }

    public CommonProductTestBuilder withPrice(BigDecimal price) {
        final var copy = new CommonProductTestBuilder(this);
        copy.price = price;
        return copy;
    }

    public CommonProductTestBuilder withSaleTypes(Set<SaleType> saleTypes) {
        final var copy = new CommonProductTestBuilder(this);
        copy.saleTypes = saleTypes;
        return copy;
    }

    @Override
    public CommonProduct build() {
        return new CommonProduct(id, name, price, saleTypes);
    }
}

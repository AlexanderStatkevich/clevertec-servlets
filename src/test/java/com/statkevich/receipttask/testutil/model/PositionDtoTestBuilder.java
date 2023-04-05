package com.statkevich.receipttask.testutil.model;

import com.statkevich.receipttask.domain.CommonProduct;
import com.statkevich.receipttask.dto.PositionDto;
import com.statkevich.receipttask.testutil.api.Builder;

import static com.statkevich.receipttask.testutil.model.CommonProductTestBuilder.aProduct;

public class PositionDtoTestBuilder implements Builder<PositionDto> {

    private CommonProduct product = aProduct().build();
    private int quantity = 6;

    private PositionDtoTestBuilder() {
    }

    private PositionDtoTestBuilder(PositionDtoTestBuilder builder) {
        this.product = builder.product;
        this.quantity = builder.quantity;
    }

    public static PositionDtoTestBuilder aPositionDto() {
        return new PositionDtoTestBuilder();
    }

    public PositionDtoTestBuilder withProduct(CommonProduct product) {
        final var copy = new PositionDtoTestBuilder(this);
        copy.product = product;
        return copy;
    }

    public PositionDtoTestBuilder withQuantity(int quantity) {
        final var copy = new PositionDtoTestBuilder(this);
        copy.quantity = quantity;
        return copy;
    }

    @Override
    public PositionDto build() {
        return new PositionDto(product, quantity);
    }
}

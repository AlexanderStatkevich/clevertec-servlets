package com.statkevich.receipttask.testutil.model;

import com.statkevich.receipttask.domain.DiscountCard;
import com.statkevich.receipttask.testutil.api.Builder;

import java.math.BigDecimal;

public class DiscountCardTestBuilder implements Builder<DiscountCard> {

    private String cardNumber = "1111";
    private BigDecimal discount = BigDecimal.valueOf(0.03);

    private DiscountCardTestBuilder() {
    }

    private DiscountCardTestBuilder(DiscountCardTestBuilder builder) {
        this.cardNumber = builder.cardNumber;
        this.discount = builder.discount;
    }

    public static DiscountCardTestBuilder aCard() {
        return new DiscountCardTestBuilder();
    }

    public DiscountCardTestBuilder withNumber(String cardNumber) {
        final var copy = new DiscountCardTestBuilder(this);
        copy.cardNumber = cardNumber;
        return copy;
    }

    public DiscountCardTestBuilder withDiscount(BigDecimal discount) {
        final var copy = new DiscountCardTestBuilder(this);
        copy.discount = discount;
        return copy;
    }

    @Override
    public DiscountCard build() {
        return new DiscountCard(cardNumber, discount);
    }
}

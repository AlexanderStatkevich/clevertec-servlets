package com.statkevich.receipttask.domain;

import com.statkevich.receipttask.annotation.CacheKey;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
@Builder
public class DiscountCard {
    @CacheKey
    private final String cardNumber;
    private final BigDecimal discount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCard that = (DiscountCard) o;
        return Objects.equals(cardNumber, that.cardNumber)
                && (discount.compareTo(that.discount) == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, discount);
    }
}

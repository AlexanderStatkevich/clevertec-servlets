package com.statkevich.receipttask.domain.dto;

import java.math.BigDecimal;

public record DiscountCardDto(
        String cardNumber,
        BigDecimal discount
) {
}

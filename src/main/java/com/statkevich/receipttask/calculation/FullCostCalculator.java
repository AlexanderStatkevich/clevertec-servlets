package com.statkevich.receipttask.calculation;

import com.statkevich.receipttask.domain.CommonProduct;
import com.statkevich.receipttask.dto.PositionDto;
import com.statkevich.receipttask.dto.ReceiptRow;

import java.math.BigDecimal;

public class FullCostCalculator implements Calculator {

    @Override
    public ReceiptRow calculate(PositionDto position) {
        CommonProduct product = position.product();
        BigDecimal price = product.getPrice();
        String name = product.getName();
        int quantity = position.quantity();
        BigDecimal totalRow = price.multiply(BigDecimal.valueOf(quantity));
        return new ReceiptRow(quantity, name, price, BigDecimal.ZERO, totalRow, BigDecimal.ZERO);
    }
}

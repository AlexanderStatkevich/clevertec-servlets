package com.statkevich.receipttask.dto;

import java.math.BigDecimal;
import java.util.Objects;

public record ReceiptRow(int quantity, String productName, BigDecimal price, BigDecimal salePercentage,
                         BigDecimal totalRow, BigDecimal saleAmount) {
    private static boolean equalsBigDecimalStripTrailingZeros(BigDecimal first, BigDecimal second) {
        return first.stripTrailingZeros().compareTo(second.stripTrailingZeros()) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceiptRow that = (ReceiptRow) o;
        return quantity == that.quantity
                && Objects.equals(productName, that.productName)
                && equalsBigDecimalStripTrailingZeros(price, that.price)
                && equalsBigDecimalStripTrailingZeros(salePercentage, that.salePercentage)
                && equalsBigDecimalStripTrailingZeros(totalRow, that.totalRow)
                && equalsBigDecimalStripTrailingZeros(saleAmount, that.saleAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, productName, price.stripTrailingZeros(), salePercentage.stripTrailingZeros(), totalRow.stripTrailingZeros(), saleAmount.stripTrailingZeros());
    }
}

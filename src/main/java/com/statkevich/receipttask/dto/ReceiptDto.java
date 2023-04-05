package com.statkevich.receipttask.dto;


import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public record ReceiptDto(List<ReceiptRow> receiptRow, BigDecimal total) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceiptDto that = (ReceiptDto) o;
        return Objects.equals(receiptRow, that.receiptRow)
                && (total.compareTo(that.total) == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiptRow, total);
    }
}

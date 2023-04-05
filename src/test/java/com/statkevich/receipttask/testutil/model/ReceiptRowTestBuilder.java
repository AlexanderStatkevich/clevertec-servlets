package com.statkevich.receipttask.testutil.model;

import com.statkevich.receipttask.dto.ReceiptRow;
import com.statkevich.receipttask.testutil.api.Builder;

import java.math.BigDecimal;

public class ReceiptRowTestBuilder implements Builder<ReceiptRow> {
    private int quantity = 6;
    private String productName = "NAME";
    private BigDecimal price = BigDecimal.valueOf(5);
    private BigDecimal salePercentage = BigDecimal.valueOf(10);
    private BigDecimal totalRow = BigDecimal.valueOf(27);
    private BigDecimal saleAmount = BigDecimal.valueOf(3);

    private ReceiptRowTestBuilder() {
    }

    private ReceiptRowTestBuilder(ReceiptRowTestBuilder builder) {
        this.quantity = builder.quantity;
        this.productName = builder.productName;
        this.price = builder.price;
        this.salePercentage = builder.salePercentage;
        this.totalRow = builder.totalRow;
        this.saleAmount = builder.saleAmount;
    }

    public static ReceiptRowTestBuilder aReceiptRow() {
        return new ReceiptRowTestBuilder();
    }

    public ReceiptRowTestBuilder withQuantity(int quantity) {
        final var copy = new ReceiptRowTestBuilder(this);
        copy.quantity = quantity;
        return copy;
    }

    public ReceiptRowTestBuilder withProductName(String productName) {
        final var copy = new ReceiptRowTestBuilder(this);
        copy.productName = productName;
        return copy;
    }

    public ReceiptRowTestBuilder withPrice(BigDecimal price) {
        final var copy = new ReceiptRowTestBuilder(this);
        copy.price = price;
        return copy;
    }

    public ReceiptRowTestBuilder withSalePercentage(BigDecimal salePercentage) {
        final var copy = new ReceiptRowTestBuilder(this);
        copy.salePercentage = salePercentage;
        return copy;
    }

    public ReceiptRowTestBuilder withTotalRow(BigDecimal totalRow) {
        final var copy = new ReceiptRowTestBuilder(this);
        copy.totalRow = totalRow;
        return copy;
    }

    public ReceiptRowTestBuilder withSaleAmount(BigDecimal saleAmount) {
        final var copy = new ReceiptRowTestBuilder(this);
        copy.saleAmount = saleAmount;
        return copy;
    }

    @Override
    public ReceiptRow build() {
        return new ReceiptRow(quantity, productName, price, salePercentage, totalRow, saleAmount);
    }
}

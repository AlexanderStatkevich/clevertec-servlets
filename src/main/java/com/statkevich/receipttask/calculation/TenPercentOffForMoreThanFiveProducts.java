package com.statkevich.receipttask.calculation;

import com.statkevich.receipttask.domain.CommonProduct;
import com.statkevich.receipttask.domain.SaleType;
import com.statkevich.receipttask.dto.PositionDto;
import com.statkevich.receipttask.dto.ReceiptRow;

import java.math.BigDecimal;
import java.util.Set;

public class TenPercentOffForMoreThanFiveProducts extends CalculatorDecorator {
    public static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    private static final int MIN_QUANTITY_FOR_SALE = 5;
    private static final BigDecimal MULTIPLIER = (BigDecimal.valueOf(0.9));

    public TenPercentOffForMoreThanFiveProducts(Calculator calculator) {
        super(calculator);
    }

    private static ReceiptRow applySale(ReceiptRow receiptRow, int quantity) {
        String name = receiptRow.productName();
        BigDecimal price = receiptRow.price();
        BigDecimal totalRowWithoutSale = receiptRow.totalRow();
        BigDecimal totalRowSalePrice = totalRowWithoutSale.multiply(MULTIPLIER);
        BigDecimal salePercentage = BigDecimal.ONE.subtract(MULTIPLIER).multiply(HUNDRED);
        BigDecimal saleAmount = totalRowWithoutSale.subtract(totalRowSalePrice);
        return new ReceiptRow(quantity, name, price, salePercentage, totalRowSalePrice, saleAmount);
    }

    @Override
    public ReceiptRow calculate(PositionDto position) {
        ReceiptRow receiptRow = super.calculate(position);
        CommonProduct product = position.product();
        Set<SaleType> saleTypes = product.getSaleTypes();
        int quantity = receiptRow.quantity();

        if (quantity > MIN_QUANTITY_FOR_SALE
                && saleTypes.contains(SaleType.TEN_PERCENT_OFF_FOR_MORE_THAN_FIVE_PRODUCTS)) {
            return applySale(receiptRow, quantity);
        } else {
            return receiptRow;
        }
    }
}

package com.statkevich.receipttask.calculation;

import com.statkevich.receipttask.domain.DiscountCard;
import com.statkevich.receipttask.dto.PositionDto;
import com.statkevich.receipttask.dto.ReceiptRow;

import java.math.BigDecimal;
import java.util.Objects;

public class DiscountCardDecorator extends CalculatorDecorator {

    public static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    private final DiscountCard discountCard;

    public DiscountCardDecorator(Calculator calculator, DiscountCard discountCard) {
        super(calculator);
        this.discountCard = discountCard;
    }

    @Override
    public ReceiptRow calculate(PositionDto position) {
        ReceiptRow receiptRow = super.calculate(position);
        BigDecimal salePercentage = receiptRow.salePercentage();
        boolean saleAlreadyApplied = !Objects.equals(salePercentage, BigDecimal.ZERO);

        return saleAlreadyApplied
                ? receiptRow
                : applyDiscountCard(receiptRow);
    }

    private ReceiptRow applyDiscountCard(ReceiptRow receiptRow) {
        String name = receiptRow.productName();
        BigDecimal price = receiptRow.price();
        int quantity = receiptRow.quantity();
        BigDecimal totalRow = receiptRow.totalRow();
        BigDecimal discount = discountCard.getDiscount();
        BigDecimal discountMultiplier = (BigDecimal.ONE.subtract(discount));
        BigDecimal discountCardPercentage = discount.multiply(HUNDRED);
        BigDecimal totalRowDiscountCardPrice = totalRow.multiply(discountMultiplier);
        BigDecimal saleAmount = totalRow.subtract(totalRowDiscountCardPrice);
        return new ReceiptRow(quantity, name, price, discountCardPercentage, totalRowDiscountCardPrice, saleAmount);
    }
}

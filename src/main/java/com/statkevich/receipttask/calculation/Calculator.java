package com.statkevich.receipttask.calculation;


import com.statkevich.receipttask.dto.PositionDto;
import com.statkevich.receipttask.dto.ReceiptRow;

/**
 * Described class used to calculate single position from purchase.
 * Must be implemented by {@link com.statkevich.receipttask.calculation.FullCostCalculator}
 * and {@link com.statkevich.receipttask.calculation.CalculatorDecorator}
 */
public interface Calculator {
    /**
     * @param position object containing info about product and it's quantity
     * @return a row which will be combined into receipt
     */
    ReceiptRow calculate(PositionDto position);
}

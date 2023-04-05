package com.statkevich.receipttask.calculation;

import com.statkevich.receipttask.dto.PositionDto;
import com.statkevich.receipttask.dto.ReceiptRow;

public abstract class CalculatorDecorator implements Calculator {

    private final Calculator calculator;

    public CalculatorDecorator(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public ReceiptRow calculate(PositionDto position) {
        return calculator.calculate(position);
    }
}

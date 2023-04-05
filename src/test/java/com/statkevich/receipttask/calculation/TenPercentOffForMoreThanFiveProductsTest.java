package com.statkevich.receipttask.calculation;

import com.statkevich.receipttask.dto.PositionDto;
import com.statkevich.receipttask.dto.ReceiptRow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.statkevich.receipttask.testutil.model.PositionDtoTestBuilder.aPositionDto;
import static com.statkevich.receipttask.testutil.model.ReceiptRowTestBuilder.aReceiptRow;
import static org.assertj.core.api.Assertions.assertThat;

class TenPercentOffForMoreThanFiveProductsTest {

    @Test
    @DisplayName("Check -10% calculation")
    void checkCalculate() {
        TenPercentOffForMoreThanFiveProducts tenPercentOffForMoreThanFiveProducts = new TenPercentOffForMoreThanFiveProducts(new FullCostCalculator());
        PositionDto positionDto = aPositionDto().build();
        ReceiptRow actual = tenPercentOffForMoreThanFiveProducts.calculate(positionDto);
        ReceiptRow expected = aReceiptRow().build();

        assertThat(actual).isEqualTo(expected);
    }
}

package com.statkevich.receipttask.calculation;

import com.statkevich.receipttask.domain.CommonProduct;
import com.statkevich.receipttask.dto.PositionDto;
import com.statkevich.receipttask.dto.ReceiptRow;
import com.statkevich.receipttask.testutil.model.CommonProductTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.statkevich.receipttask.testutil.model.PositionDtoTestBuilder.aPositionDto;
import static com.statkevich.receipttask.testutil.model.ReceiptRowTestBuilder.aReceiptRow;
import static org.assertj.core.api.Assertions.assertThat;

class FullCostCalculatorTest {

    @Test
    @DisplayName("Check full cost calculation")
    void checkCalculate() {
        CommonProduct product = CommonProductTestBuilder.aProduct()
                .withSaleTypes(null).build();
        FullCostCalculator fullCostCalculator = new FullCostCalculator();
        PositionDto positionDto = aPositionDto().withProduct(product).build();
        ReceiptRow actual = fullCostCalculator.calculate(positionDto);
        ReceiptRow expected = aReceiptRow()
                .withSalePercentage(BigDecimal.ZERO)
                .withTotalRow(BigDecimal.valueOf(30))
                .withSaleAmount(BigDecimal.ZERO).build();

        assertThat(actual).isEqualTo(expected);
    }
}

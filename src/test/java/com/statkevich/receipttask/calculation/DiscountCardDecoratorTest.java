package com.statkevich.receipttask.calculation;

import com.statkevich.receipttask.domain.DiscountCard;
import com.statkevich.receipttask.dto.PositionDto;
import com.statkevich.receipttask.dto.ReceiptRow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.statkevich.receipttask.testutil.model.PositionDtoTestBuilder.aPositionDto;
import static com.statkevich.receipttask.testutil.model.ReceiptRowTestBuilder.aReceiptRow;
import static org.assertj.core.api.Assertions.assertThat;

class DiscountCardDecoratorTest {

    private static Stream<Arguments> providePositionsAndReceiptRowsToTest() {
        return Stream.of(
                Arguments.of(
                        aPositionDto().build(),
                        aReceiptRow().build()
                ),
                Arguments.of(
                        aPositionDto()
                                .withQuantity(4).build(),
                        aReceiptRow()
                                .withQuantity(4)
                                .withSalePercentage(BigDecimal.valueOf(5))
                                .withTotalRow(BigDecimal.valueOf(19))
                                .withSaleAmount(BigDecimal.valueOf(1))
                                .build()
                )
        );
    }

    @DisplayName("Check decorator calculation")
    @ParameterizedTest
    @MethodSource("providePositionsAndReceiptRowsToTest")
    void checkCalculatedReceiptRow(PositionDto toCalculate, ReceiptRow expected) {
        DiscountCardDecorator discountCardDecorator = new DiscountCardDecorator(
                new TenPercentOffForMoreThanFiveProducts(
                        new FullCostCalculator()),
                new DiscountCard("1234",
                        BigDecimal.valueOf(0.05)));

        ReceiptRow actual = discountCardDecorator.calculate(toCalculate);

        assertThat(actual).isEqualTo(expected);
    }
}

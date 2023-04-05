package com.statkevich.receipttask.service;

import com.statkevich.receipttask.domain.dto.DiscountCardDto;
import com.statkevich.receipttask.dto.OrderDto;
import com.statkevich.receipttask.dto.ReceiptDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static com.statkevich.receipttask.testutil.model.CommonProductTestBuilder.aProduct;
import static com.statkevich.receipttask.testutil.model.PositionDtoTestBuilder.aPositionDto;
import static com.statkevich.receipttask.testutil.model.ReceiptRowTestBuilder.aReceiptRow;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private DiscountCardService discountCardService;
    private OrderService orderService;

    @BeforeEach
    void init() {
        orderService = new OrderService(discountCardService);
    }

    @Nested
    @DisplayName("Tests on processingOrder method")
    class ProcessingOrderTests {
        @Test
        void checkOutputOfServiceEvaluationWithCard() {
            OrderDto orderDtoWithCard = new OrderDto(
                    List.of(aPositionDto().build(),
                            aPositionDto().withProduct(aProduct()
                                            .withPrice(BigDecimal.valueOf(10))
                                            .withSaleTypes(Set.of()).build())
                                    .withQuantity(3).build()),
                    "1111");

            when(discountCardService.get("1111")).thenReturn(new DiscountCardDto("1111", BigDecimal.valueOf(0.03)));

            ReceiptDto actual = orderService.processingOrder(orderDtoWithCard);

            ReceiptDto expected = new ReceiptDto(List.of(
                    aReceiptRow().build(),
                    aReceiptRow()
                            .withQuantity(3)
                            .withPrice(BigDecimal.valueOf(10))
                            .withSalePercentage(BigDecimal.valueOf(3))
                            .withTotalRow(BigDecimal.valueOf(29.1))
                            .withSaleAmount(BigDecimal.valueOf(0.9))
                            .build()),
                    BigDecimal.valueOf(56.1));

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void checkOutputOfServiceEvaluationWithoutCard() {
            OrderDto orderDtoWithCard = new OrderDto(
                    List.of(aPositionDto().build(),
                            aPositionDto().withProduct(aProduct()
                                            .withPrice(BigDecimal.valueOf(10))
                                            .withSaleTypes(Set.of()).build())
                                    .withQuantity(3).build()),
                    null);

            ReceiptDto actual = orderService.processingOrder(orderDtoWithCard);

            ReceiptDto expected = new ReceiptDto(List.of(
                    aReceiptRow().build(),
                    aReceiptRow()
                            .withQuantity(3)
                            .withPrice(BigDecimal.valueOf(10))
                            .withSalePercentage(BigDecimal.valueOf(0))
                            .withTotalRow(BigDecimal.valueOf(30))
                            .withSaleAmount(BigDecimal.valueOf(0)).build()),
                    BigDecimal.valueOf(57));

            assertThat(actual).isEqualTo(expected);
        }
    }
}

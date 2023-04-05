package com.statkevich.receipttask.parser;

import com.statkevich.receipttask.domain.CommonProduct;
import com.statkevich.receipttask.dto.OrderDto;
import com.statkevich.receipttask.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static com.statkevich.receipttask.testutil.model.PositionDtoTestBuilder.aPositionDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsoleInputParserTest {
    private InputParser<String[]> consoleInputParser;
    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        consoleInputParser = new ConsoleInputParser(productService);
    }

    @Test
    void checkParserCorrectness() {
        when(productService.getProducts(List.of(1L))).thenReturn(List.of(new CommonProduct(1L, "Milk", BigDecimal.valueOf(2.0), Set.of())));
        String[] orderArray = new String[]{"1-2", "card-1234"};
        OrderDto expected = new OrderDto(
                List.of(aPositionDto()
                        .withProduct(CommonProduct.builder()
                                .id(1L)
                                .name("Milk")
                                .price(BigDecimal.valueOf(2.0))
                                .saleTypes(Set.of()).build())
                        .withQuantity(2).build()),
                "1234");
        OrderDto actual = consoleInputParser.parse(orderArray);
        assertThat(actual).isEqualTo(expected);
    }
}

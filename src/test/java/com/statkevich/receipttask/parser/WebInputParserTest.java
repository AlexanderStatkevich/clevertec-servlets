package com.statkevich.receipttask.parser;

import com.statkevich.receipttask.domain.CommonProduct;
import com.statkevich.receipttask.dto.OrderDto;
import com.statkevich.receipttask.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.statkevich.receipttask.testutil.model.PositionDtoTestBuilder.aPositionDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class WebInputParserTest {

    private InputParser<Map<String, String[]>> webInputParser;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        webInputParser = new WebInputParser(productService);
    }

    @Test
    void checkParserCorrectness() {
        CommonProduct milk = CommonProduct.builder()
                .id(1L)
                .name("Milk")
                .price(BigDecimal.valueOf(2.0))
                .saleTypes(Set.of()).build();

        when(productService.getProducts(List.of(1L))).
                thenReturn(List.of(milk));

        Map<String, String[]> orderMap = new HashMap<>();
        orderMap.put("1", new String[]{"2"});
        orderMap.put("card", new String[]{"1234"});

        OrderDto expected = new OrderDto(
                List.of(aPositionDto()
                        .withProduct(milk)
                        .withQuantity(2).build()),
                "1234");
        OrderDto actual = webInputParser.parse(orderMap);

        assertThat(actual).isEqualTo(expected);
    }
}

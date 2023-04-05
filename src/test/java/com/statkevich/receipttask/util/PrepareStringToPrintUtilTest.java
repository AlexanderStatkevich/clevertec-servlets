package com.statkevich.receipttask.util;

import com.statkevich.receipttask.dto.ReceiptDto;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.statkevich.receipttask.testutil.model.ReceiptRowTestBuilder.aReceiptRow;
import static org.assertj.core.api.Assertions.assertThat;

class PrepareStringToPrintUtilTest {

    private final String expected = """
                   Quantity     Description           Price           Total            Sale     Sale Amount

                          6           Name1               5              27            -10%              -3
                          3           Name2              10            29.1             -3%            -0.9

                                                     Total:            56.1
            """;


    @Test
    void checkStringUtilPrepareCorrectReceipt() throws IOException {
//        String expected = Files.readString(Path.of("src/test/resources/receipt.txt"));

        ReceiptDto receiptDto = new ReceiptDto(List.of(
                aReceiptRow().withProductName("Name1").build(),
                aReceiptRow()
                        .withQuantity(3)
                        .withProductName("Name2")
                        .withPrice(BigDecimal.valueOf(10))
                        .withSalePercentage(BigDecimal.valueOf(3))
                        .withTotalRow(BigDecimal.valueOf(29.1))
                        .withSaleAmount(BigDecimal.valueOf(0.9))
                        .build()),
                BigDecimal.valueOf(56.1));

        String actual = PrepareStringToPrintUtil.prepareReceipt(receiptDto);

        assertThat(actual).isEqualTo(expected);
    }
}

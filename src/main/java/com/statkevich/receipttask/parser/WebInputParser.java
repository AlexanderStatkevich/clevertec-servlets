package com.statkevich.receipttask.parser;

import com.statkevich.receipttask.dto.InputPositionDto;
import com.statkevich.receipttask.dto.InputValuesDto;
import com.statkevich.receipttask.service.ProductService;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class WebInputParser extends BaseInputParser<Map<String, String[]>> {

    private static final Pattern ONLY_DIGITS_PATTERN = Pattern.compile("\\d+");

    public WebInputParser(ProductService productService) {
        super(productService);
    }

    private static boolean isOnlyDigits(Map.Entry<String, String[]> entry) {
        return ONLY_DIGITS_PATTERN.matcher(entry.getKey()).matches();
    }

    @Override
    protected InputValuesDto parseInternal(Map<String, String[]> input) {
        String cardNumber = null;
        if (input.containsKey(CARD)) {
            cardNumber = input.get(CARD)[0];
        }
        List<InputPositionDto> inputPositionDto = input.entrySet().stream()
                .filter(WebInputParser::isOnlyDigits)
                .map(this::getInputPositionDto)
                .toList();

        return new InputValuesDto(inputPositionDto, cardNumber);
    }

    private InputPositionDto getInputPositionDto(Map.Entry<String, String[]> entry) {
        Long id = Long.valueOf(entry.getKey());
        int quantity = Integer.parseInt(entry.getValue()[0]);

        return new InputPositionDto(id, quantity);
    }
}

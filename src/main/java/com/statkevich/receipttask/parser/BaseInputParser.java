package com.statkevich.receipttask.parser;

import com.statkevich.receipttask.domain.CommonProduct;
import com.statkevich.receipttask.dto.InputPositionDto;
import com.statkevich.receipttask.dto.InputValuesDto;
import com.statkevich.receipttask.dto.OrderDto;
import com.statkevich.receipttask.dto.PositionDto;
import com.statkevich.receipttask.service.ProductService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BaseInputParser<T> implements InputParser<T> {
    public static final String CARD = "card";

    private final ProductService productService;

    protected BaseInputParser(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public OrderDto parse(T input) {
        InputValuesDto inputValuesDto = parseInternal(input);
        String cardNumber = inputValuesDto.cardNumber();
        List<Long> inputIds = inputValuesDto.inputPositionDtoList()
                .stream()
                .map(InputPositionDto::id)
                .collect(Collectors.toList());

        Map<Long, CommonProduct> productMap = productService.getProducts(inputIds).stream()
                .collect(Collectors.toMap(CommonProduct::getId, Function.identity()));

        List<PositionDto> positionDtoList = inputValuesDto.inputPositionDtoList()
                .stream()
                .map(position -> new PositionDto(productMap.get(position.id()), position.quantity()))
                .collect(Collectors.toList());

        return new OrderDto(positionDtoList, cardNumber);
    }

    protected abstract InputValuesDto parseInternal(T input);

}

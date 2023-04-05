package com.statkevich.receipttask.parser;

import com.statkevich.receipttask.dto.InputValuesDto;
import com.statkevich.receipttask.reader.ConsoleReader;
import com.statkevich.receipttask.reader.FileReader;
import com.statkevich.receipttask.service.ProductService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsoleInputParser extends BaseInputParser<String[]> {

    private final ConsoleReader consoleReader = new ConsoleReader();
    private final FileReader fileReader = new FileReader(consoleReader);

    public ConsoleInputParser(ProductService productService) {
        super(productService);
    }

    @Override
    protected InputValuesDto parseInternal(String[] order) {
        List<String> orderList = new ArrayList<>(Arrays.asList(order));

        boolean parameterFile = orderList.stream()
                .anyMatch(row -> row.contains("file"));

        if (parameterFile) {
            return fileReader.read(orderList);
        } else {
            return consoleReader.read(orderList);
        }
    }
}

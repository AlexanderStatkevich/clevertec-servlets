package com.statkevich.receipttask.parser;

import com.statkevich.receipttask.dto.OrderDto;

public interface InputParser<T> {
    OrderDto parse(T input);
}

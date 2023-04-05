package com.statkevich.receipttask.reader;

import com.statkevich.receipttask.dto.InputValuesDto;

import java.util.List;

public interface Reader {

    InputValuesDto read(List<String> orderList);

}

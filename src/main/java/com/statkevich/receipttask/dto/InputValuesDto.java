package com.statkevich.receipttask.dto;

import java.util.List;

public record InputValuesDto(List<InputPositionDto> inputPositionDtoList, String cardNumber) {
}

package com.statkevich.receipttask.dto;

import java.util.List;

public record InputValueDto(List<InputPositionDto> inputPositionDtoList, String cardNumber) {
}

package com.statkevich.receipttask.dto;

import java.util.List;

public record OrderDto(List<PositionDto> positionDtoList, String cardNumber) {
}

package com.statkevich.receipttask.dto;

import com.statkevich.receipttask.domain.CommonProduct;

public record PositionDto(CommonProduct product, int quantity) {

}

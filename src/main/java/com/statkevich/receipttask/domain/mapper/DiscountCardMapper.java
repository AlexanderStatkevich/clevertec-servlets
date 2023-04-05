package com.statkevich.receipttask.domain.mapper;

import com.statkevich.receipttask.domain.DiscountCard;
import com.statkevich.receipttask.domain.dto.DiscountCardDto;
import org.mapstruct.Mapper;

@Mapper
public interface DiscountCardMapper {
    DiscountCard toEntity(DiscountCardDto source);

    DiscountCardDto toDto(DiscountCard source);
}

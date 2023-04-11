package com.statkevich.receipttask.domain.mapper;

import com.statkevich.receipttask.domain.DiscountCard;
import com.statkevich.receipttask.domain.dto.DiscountCardDto;
import org.mapstruct.Mapper;

@Mapper
public interface DiscountCardMapper extends EntityMapper<DiscountCard, DiscountCardDto> {
    DiscountCard toEntity(DiscountCardDto source);

    @Override
    DiscountCardDto toDto(DiscountCard source);
}

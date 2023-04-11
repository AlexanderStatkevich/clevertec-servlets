package com.statkevich.receipttask.domain.mapper;

import com.statkevich.receipttask.domain.CommonProduct;
import com.statkevich.receipttask.domain.dto.ProductCreateDto;
import com.statkevich.receipttask.domain.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper extends EntityMapper<CommonProduct, ProductDto> {

    CommonProduct toEntity(ProductDto source);

    CommonProduct toCreateEntity(ProductCreateDto source);

    @Override
    ProductDto toDto(CommonProduct source);
}

package com.statkevich.receipttask.service.api;

import com.statkevich.receipttask.domain.dto.ProductCreateDto;
import com.statkevich.receipttask.domain.dto.ProductDto;
import com.statkevich.receipttask.dto.PageDto;

public interface IProductService {

    void save(ProductCreateDto product);

    ProductDto get(Long id);

    PageDto<ProductDto> getPage(Long pageNumber, Long pageSize);

    void update(ProductDto product);

    void delete(Long id);
}

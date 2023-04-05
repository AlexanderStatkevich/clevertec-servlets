package com.statkevich.receipttask.service.api;

import com.statkevich.receipttask.domain.dto.ProductCreateDto;
import com.statkevich.receipttask.domain.dto.ProductDto;

public interface IProductService {

    void save(ProductCreateDto product);

    ProductDto get(Long id);

    void update(ProductDto product);

    void delete(Long id);
}

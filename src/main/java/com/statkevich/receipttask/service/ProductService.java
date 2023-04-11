package com.statkevich.receipttask.service;

import com.statkevich.receipttask.dao.api.ProductDao;
import com.statkevich.receipttask.domain.CommonProduct;
import com.statkevich.receipttask.domain.dto.ProductCreateDto;
import com.statkevich.receipttask.domain.dto.ProductDto;
import com.statkevich.receipttask.domain.mapper.ProductMapper;
import com.statkevich.receipttask.domain.mapper.ProductMapperImpl;
import com.statkevich.receipttask.dto.PageDto;
import com.statkevich.receipttask.service.api.IProductService;
import com.statkevich.receipttask.util.PageMapper;

import java.util.List;

/**
 * Described class bind storage and output of application.
 * As well as uses CRUD operations for this purpose.
 */
public class ProductService implements IProductService {

    private final ProductDao productDao;

    private final ProductMapper mapper = new ProductMapperImpl();

    private final PageMapper<CommonProduct, ProductDto> pageMapper = new PageMapper<>(mapper);

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<CommonProduct> getProducts(List<Long> ids) {
        return productDao.getByKeys(ids);
    }

    @Override
    public void save(ProductCreateDto product) {
        CommonProduct commonProduct = mapper.toCreateEntity(product);
        productDao.save(commonProduct);
    }

    @Override
    public ProductDto get(Long id) {
        CommonProduct commonProduct = productDao.get(id);
        return mapper.toDto(commonProduct);
    }

    @Override
    public PageDto<ProductDto> getPage(Long pageNumber, Long pageSize) {
        PageDto<CommonProduct> page = productDao.getPage(pageNumber, pageSize);
        return pageMapper.mapToDto(page);
    }

    @Override
    public void update(ProductDto product) {
        CommonProduct commonProduct = mapper.toEntity(product);
        productDao.update(commonProduct);
    }

    @Override
    public void delete(Long id) {
        productDao.delete(id);
    }
}

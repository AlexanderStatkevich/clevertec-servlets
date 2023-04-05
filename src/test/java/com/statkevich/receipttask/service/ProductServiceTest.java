package com.statkevich.receipttask.service;

import com.statkevich.receipttask.dao.api.ProductDao;
import com.statkevich.receipttask.domain.CommonProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.statkevich.receipttask.testutil.model.CommonProductTestBuilder.aProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductDao productDao;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productDao);
    }

    @Test
    void getProducts() {
        List<CommonProduct> expected = List.of(
                aProduct()
                        .withSaleTypes(null)
                        .build());

        when(productDao.getByKeys(List.of(1L))).thenReturn(expected);
        List<CommonProduct> actual = productService.getProducts(List.of(1L));

        assertThat(actual).isEqualTo(expected);
    }
}

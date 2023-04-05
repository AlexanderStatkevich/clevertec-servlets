package com.statkevich.receipttask.cache;

import com.statkevich.receipttask.domain.CommonProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.statkevich.receipttask.testutil.model.CommonProductTestBuilder.aProduct;
import static org.assertj.core.api.Assertions.assertThat;

class LfuCacheTest {

    private final LfuCache lfuCache = new LfuCache(10);

    @Test
    @DisplayName("Check put method")
    void put() {
        CommonProduct firstAddedProduct = aProduct().build();
        lfuCache.put(1L, firstAddedProduct);
        CommonProduct secondAddedProduct = aProduct().withId(10L).build();
        lfuCache.put(1L, secondAddedProduct);

        CommonProduct cachedProduct = (CommonProduct) lfuCache.get(1L);
        assertThat(cachedProduct).isEqualTo(secondAddedProduct);
        assertThat(cachedProduct).isNotEqualTo(firstAddedProduct);
    }

    @Test
    @DisplayName("Check get method")
    void get() {
        CommonProduct firstAddedProduct = aProduct().build();
        lfuCache.put(1L, firstAddedProduct);
        CommonProduct secondAddedProduct = aProduct().withId(10L).build();
        lfuCache.put(1L, secondAddedProduct);
        CommonProduct cachedProduct = (CommonProduct) lfuCache.get(1L);
        assertThat(cachedProduct).isEqualTo(secondAddedProduct);
    }

    @Test
    @DisplayName("Check evict method")
    void evict() {
        CommonProduct product = aProduct().build();
        lfuCache.put(1L, product);
        CommonProduct cachedProduct = (CommonProduct) lfuCache.get(1L);
        assertThat(cachedProduct).isEqualTo(product);
        lfuCache.evict(1L);
        CommonProduct afterEvictionProduct = (CommonProduct) lfuCache.get(1L);
        assertThat(afterEvictionProduct).isNull();
    }
}

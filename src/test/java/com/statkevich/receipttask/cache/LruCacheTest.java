package com.statkevich.receipttask.cache;

import com.statkevich.receipttask.domain.CommonProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.statkevich.receipttask.testutil.model.CommonProductTestBuilder.aProduct;
import static org.assertj.core.api.Assertions.assertThat;

class LruCacheTest {
    private final LruCache lruCache = new LruCache(10);

    @Test
    @DisplayName("Check put method")
    void checkThatPutMethodUpdateObjectsInCache() {
        CommonProduct firstAddedProduct = aProduct().build();
        lruCache.put(1L, firstAddedProduct);
        CommonProduct secondAddedProduct = aProduct().withId(10L).build();
        lruCache.put(1L, secondAddedProduct);
        CommonProduct cachedProduct = (CommonProduct) lruCache.get(1L);
        assertThat(cachedProduct).isNotEqualTo(firstAddedProduct);
    }

    @Test
    @DisplayName("Check get method")
    void checkThatGetMethodRetrieveActualObject() {
        CommonProduct firstAddedProduct = aProduct().build();
        lruCache.put(1L, firstAddedProduct);
        CommonProduct secondAddedProduct = aProduct().withId(10L).build();
        lruCache.put(1L, secondAddedProduct);
        CommonProduct cachedProduct = (CommonProduct) lruCache.get(1L);
        assertThat(cachedProduct).isEqualTo(secondAddedProduct);
    }

    @Test
    @DisplayName("Check evict method")
    void checkThatEvictedObjectDoesNotContainsInCache() {
        CommonProduct product = aProduct().build();
        lruCache.put(1L, product);
        CommonProduct cachedProduct = (CommonProduct) lruCache.get(1L);
        assertThat(cachedProduct).isEqualTo(product);
        lruCache.evict(1L);
        CommonProduct afterEvictionProduct = (CommonProduct) lruCache.get(1L);
        assertThat(afterEvictionProduct).isNull();
    }
}

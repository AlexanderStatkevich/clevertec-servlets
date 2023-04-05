package com.statkevich.receipttask.dao;

import com.statkevich.receipttask.dao.sql.SqlProductDao;
import com.statkevich.receipttask.domain.CommonProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

import static com.statkevich.receipttask.testutil.DataSourceUtil.getDataSource;
import static com.statkevich.receipttask.testutil.model.CommonProductTestBuilder.aProduct;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class SqlProductDaoTest {
    @Container
    private final PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres:15.1-alpine")
            .withDatabaseName("receiptDB")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("test.sql");
    private DataSource dataSource;
    private SqlProductDao productDao;

    @BeforeEach
    void init() {
        dataSource = getDataSource(postgreSqlContainer);
        productDao = new SqlProductDao(dataSource);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = """
                    DELETE FROM products;
                    INSERT INTO products (id, name, price, sale_types)
                    VALUES (1, 'Milk', 5.0, '{}'::varchar[]),
                           (2, 'Bread', 3.0, '{TEN_PERCENT_OFF_FOR_MORE_THAN_FIVE_PRODUCTS}'::varchar[]),
                           (3, 'Meat', 15.0, '{}'::varchar[]);
                                                   """;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkGetMethodReturnAppropriateEntity() {
        CommonProduct expected = aProduct()
                .withId(1L)
                .withName("Milk")
                .withSaleTypes(Set.of())
                .build();
        CommonProduct actual = productDao.get(1L);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkGetByKeysReturnAppropriateEntities() {
        CommonProduct commonProduct1 = aProduct()
                .withName("Milk")
                .withSaleTypes(Set.of())
                .build();
        CommonProduct commonProduct2 = aProduct()
                .withId(2L)
                .withName("Bread")
                .withPrice(BigDecimal.valueOf(3.0))
                .build();
        CommonProduct commonProduct3 = aProduct()
                .withId(3L)
                .withName("Meat")
                .withPrice(BigDecimal.valueOf(15.0))
                .withSaleTypes(Set.of())
                .build();
        List<Long> ids = List.of(1L, 2L, 3L);
        List<CommonProduct> expected = List.of(commonProduct1, commonProduct2, commonProduct3);
        List<CommonProduct> actual = productDao.getByKeys(ids);
        assertThat(actual).isEqualTo(expected);
    }
}

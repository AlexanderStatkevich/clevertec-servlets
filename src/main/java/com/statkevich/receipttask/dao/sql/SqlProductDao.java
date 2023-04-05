package com.statkevich.receipttask.dao.sql;

import com.statkevich.receipttask.annotation.CacheEvict;
import com.statkevich.receipttask.annotation.CachePut;
import com.statkevich.receipttask.annotation.Cacheable;
import com.statkevich.receipttask.dao.api.ProductDao;
import com.statkevich.receipttask.domain.CommonProduct;
import com.statkevich.receipttask.domain.SaleType;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SqlProductDao extends SqlBaseDao<CommonProduct, Long> implements ProductDao {

    private static final String SAVE_QUERY = """
            INSERT INTO  products (name, price, sale_types)
            VALUES (?,?,?);""";

    private static final String READ_BY_ID_QUERY = """
            SELECT id,name,price,sale_types
            from products
            WHERE id = ? ;""";

    private static final String READ_BY_IDS_QUERY = """
            SELECT id,name,price,sale_types
            from products
            WHERE id = any (?);""";

    private static final String UPDATE_QUERY = """
            UPDATE products
            SET name = ?, price = ?,sale_types = ?
            WHERE id=?;""";

    private static final String DELETE_QUERY = """
            DELETE from products
            where id=?;""";

    public SqlProductDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    @Cacheable(name = "product")
    public CommonProduct get(Long key) {
        return super.get(key);
    }


    @Override
    @CachePut(name = "product")
    public void save(CommonProduct entity) {
        super.save(entity);
    }

    @Override
    @CacheEvict(name = "product")
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @CachePut(name = "product")
    public CommonProduct update(CommonProduct entity) {
        super.update(entity);
        return entity;
    }

    @Override
    protected void saveInternal(PreparedStatement preparedStatement, CommonProduct commonProduct) throws SQLException {
        preparedStatement.setString(1, commonProduct.getName());
        preparedStatement.setBigDecimal(2, commonProduct.getPrice());
        Set<SaleType> saleTypes = commonProduct.getSaleTypes();
        Object[] saleTypesArray = saleTypes.toArray();
        Connection connection = preparedStatement.getConnection();
        Array saleTypesToDb = connection.createArrayOf("VARCHAR", saleTypesArray);
        preparedStatement.setArray(3, saleTypesToDb);
    }

    @Override
    protected ResultSet getByKeysInternal(PreparedStatement preparedStatement, List<Long> keyList) throws SQLException {
        Object[] keyArray = keyList.toArray();
        Connection connection = preparedStatement.getConnection();
        Array keys = connection.createArrayOf("bigint", keyArray);
        preparedStatement.setArray(1, keys);
        return preparedStatement.executeQuery();
    }

    @Override
    protected ResultSet getInternal(PreparedStatement preparedStatement, Long key) throws SQLException {
        preparedStatement.setLong(1, key);
        return preparedStatement.executeQuery();
    }

    @Override
    protected void updateInternal(PreparedStatement preparedStatement, CommonProduct commonProduct) throws SQLException {
        Long id = commonProduct.getId();
        String name = commonProduct.getName();
        BigDecimal price = commonProduct.getPrice();
        Set<SaleType> saleTypes = commonProduct.getSaleTypes();
        Object[] saleTypesArray = saleTypes.toArray();
        Connection connection = preparedStatement.getConnection();
        Array saleTypesToDb = connection.createArrayOf("bigint", saleTypesArray);
        preparedStatement.setString(1, name);
        preparedStatement.setBigDecimal(2, price);
        preparedStatement.setArray(3, saleTypesToDb);
        preparedStatement.setLong(4, id);
        preparedStatement.executeUpdate();
    }

    @Override
    protected CommonProduct buildEntity(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        BigDecimal price = resultSet.getBigDecimal("price");
        Object sale_types = resultSet.getArray("sale_types").getArray();
        String[] saleTypesArray = (String[]) sale_types;
        Set<SaleType> saleTypes = Arrays.stream(saleTypesArray)
                .map(SaleType::valueOf)
                .collect(Collectors.toSet());

        return CommonProduct.builder()
                .id(id)
                .name(name)
                .price(price)
                .saleTypes(saleTypes)
                .build();
    }

    @Override
    protected String getSaveQuery() {
        return SAVE_QUERY;
    }

    @Override
    protected String getQuery() {
        return READ_BY_ID_QUERY;
    }

    @Override
    protected String getByKeysQuery() {
        return READ_BY_IDS_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_QUERY;
    }
}

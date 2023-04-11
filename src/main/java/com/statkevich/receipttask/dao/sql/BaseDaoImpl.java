package com.statkevich.receipttask.dao.sql;

import com.statkevich.receipttask.dao.api.BaseDao;
import com.statkevich.receipttask.dto.PageDto;
import com.statkevich.receipttask.exceptions.DataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseDaoImpl<E, K> implements BaseDao<E, K> {

    public static final long DEFAULT_PAGE_SIZE = 20;
    private final DataSource dataSource;


    public BaseDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(E entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSaveQuery(), Statement.RETURN_GENERATED_KEYS)) {
            saveInternal(preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("SQLException save method :" + e);
        }
    }

    protected abstract void saveInternal(PreparedStatement preparedStatement, E entity) throws SQLException;

    @Override
    public E get(K key) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getQuery());
             ResultSet resultSet = getInternal(preparedStatement, key)) {

            boolean hasNext = resultSet.next();
            if (hasNext) {
                return buildEntity(resultSet);
            } else {
                throw new DataAccessException("Entity with key " + key + " not found");
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQLException Get method :", e);
        }
    }

    protected abstract ResultSet getInternal(PreparedStatement preparedStatement, K key) throws SQLException;

    @Override
    public List<E> getByKeys(List<K> keyList) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getByKeysQuery());
             ResultSet resultSet = getByKeysInternal(preparedStatement, keyList)) {
            ArrayList<E> entityList = new ArrayList<>();
            while (resultSet.next()) {
                E entity = buildEntity(resultSet);
                entityList.add(entity);
            }
            if (entityList.size() == keyList.size()) {
                return entityList;
            } else {
                throw new DataAccessException("Entity not found");
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQLException GetByKeys method :" + e);
        }
    }

    protected abstract ResultSet getByKeysInternal(PreparedStatement preparedStatement, List<K> keyList) throws SQLException;

    @Override
    public PageDto<E> getPage(Long page, Long size) {
        Long pageSize = size == null
                ? DEFAULT_PAGE_SIZE
                : size;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getPageQuery());
             ResultSet resultSet = getPageContentInternal(preparedStatement, page, pageSize)) {
            ArrayList<E> entityList = new ArrayList<>();
            while (resultSet.next()) {
                E entity = buildEntity(resultSet);
                entityList.add(entity);
            }
            PageDto<E> pageWithoutTotalAndLast = PageDto.<E>builder()
                    .number(page)
                    .size(pageSize)
                    .first(page == 1)
                    .numberOfElements(entityList.size())
                    .content(entityList)
                    .build();
            return setFields(pageWithoutTotalAndLast, page, pageSize);
        } catch (SQLException e) {
            throw new DataAccessException("SQLException GetPage method :" + e);
        }
    }

    private PageDto<E> setFields(PageDto<E> pageDto, Long page, Long size) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSetFieldsQuery());
             ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            long totalRows = resultSet.getLong("total");
            long totalPages = (long) Math.ceil((double) totalRows / size);
            pageDto.setTotalPages(totalPages);
            pageDto.setLast(page == totalPages);
            return pageDto;
        } catch (SQLException e) {
            throw new DataAccessException("SQLException GetPage method :" + e);
        }
    }

    protected abstract ResultSet getPageContentInternal(PreparedStatement preparedStatement, Long page, Long size) throws SQLException;


    @Override
    public E update(E entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getUpdateQuery())) {
            updateInternal(preparedStatement, entity);
            return entity;
        } catch (SQLException e) {
            throw new DataAccessException("SQLException update method :" + e);
        }
    }

    protected abstract void updateInternal(PreparedStatement preparedStatement, E entity) throws SQLException;

    @Override
    public void delete(K id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getDeleteQuery())) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("SQLException deleteById method :" + e);
        }
    }


    protected abstract E buildEntity(ResultSet resultSet) throws SQLException;

    protected abstract String getSaveQuery();

    protected abstract String getQuery();

    protected abstract String getByKeysQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getPageQuery();

    protected abstract String getSetFieldsQuery();

}

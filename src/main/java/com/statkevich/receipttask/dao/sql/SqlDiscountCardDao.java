package com.statkevich.receipttask.dao.sql;

import com.statkevich.receipttask.dao.api.DiscountCardDao;
import com.statkevich.receipttask.domain.DiscountCard;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlDiscountCardDao extends SqlBaseDao<DiscountCard, String> implements DiscountCardDao {

    private static final String SAVE_QUERY = """
            INSERT INTO  discount_cards (card_number,discount)
            VALUES (?,?);""";
    private static final String READ_BY_NUMBER_QUERY = """
            SELECT card_number,discount
            from discount_cards
            WHERE card_number= ?;""";

    private static final String READ_BY_NUMBERS_QUERY = """
            SELECT card_number,discount
            from discount_cards
            WHERE card_number = any (?);""";
    private static final String UPDATE_QUERY = """
            UPDATE discount_cards
            SET  discount = ?
            WHERE card_number = ?;""";

    private static final String DELETE_QUERY = """
            DELETE from discount_cards
            where card_number=?;""";

    public SqlDiscountCardDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void saveInternal(PreparedStatement preparedStatement, DiscountCard discountCard) throws SQLException {
        preparedStatement.setString(1, discountCard.getCardNumber());
        preparedStatement.setBigDecimal(2, discountCard.getDiscount());
    }

    @Override
    protected ResultSet getByKeysInternal(PreparedStatement preparedStatement, List<String> cardNumbers) throws SQLException {
        String[] cardNumbersArray = cardNumbers.toArray(String[]::new);
        Connection connection = preparedStatement.getConnection();
        Array cards = connection.createArrayOf("varchar", cardNumbersArray);
        preparedStatement.setArray(1, cards);
        return preparedStatement.executeQuery();
    }

    @Override
    protected ResultSet getInternal(PreparedStatement preparedStatement, String key) throws SQLException {
        preparedStatement.setString(1, key);
        return preparedStatement.executeQuery();
    }

    @Override
    protected void updateInternal(PreparedStatement preparedStatement, DiscountCard discountCard) throws SQLException {
        String cardNumber = discountCard.getCardNumber();
        BigDecimal discount = discountCard.getDiscount();
        preparedStatement.setBigDecimal(1, discount);
        preparedStatement.setString(2, cardNumber);
        preparedStatement.executeUpdate();
    }

    @Override
    protected DiscountCard buildEntity(ResultSet resultSet) throws SQLException {
        String cardNumber = resultSet.getString("card_number");
        BigDecimal discount = resultSet.getBigDecimal("discount");

        return DiscountCard.builder()
                .cardNumber(cardNumber)
                .discount(discount)
                .build();
    }

    @Override
    protected String getSaveQuery() {
        return SAVE_QUERY;
    }

    @Override
    protected String getQuery() {
        return READ_BY_NUMBER_QUERY;
    }

    @Override
    protected String getByKeysQuery() {
        return READ_BY_NUMBERS_QUERY;
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

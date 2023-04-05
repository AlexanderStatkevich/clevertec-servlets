package com.statkevich.receipttask.service;

import com.statkevich.receipttask.dao.api.DiscountCardDao;
import com.statkevich.receipttask.domain.DiscountCard;
import com.statkevich.receipttask.domain.dto.DiscountCardDto;
import com.statkevich.receipttask.domain.mapper.DiscountCardMapper;
import com.statkevich.receipttask.domain.mapper.DiscountCardMapperImpl;
import com.statkevich.receipttask.service.api.IDiscountCardService;

/**
 * Described class bind storage and output of application.
 * As well as uses CRUD operations for this purpose.
 */
public class DiscountCardService implements IDiscountCardService {

    private final DiscountCardDao discountCardDao;

    private final DiscountCardMapper mapper = new DiscountCardMapperImpl();

    public DiscountCardService(DiscountCardDao discountCardDao) {
        this.discountCardDao = discountCardDao;
    }

    @Override
    public void save(DiscountCardDto card) {
        DiscountCard discountCard = mapper.toEntity(card);
        discountCardDao.save(discountCard);
    }

    @Override
    public DiscountCardDto get(String cardName) {
        DiscountCard discountCard = discountCardDao.get(cardName);
        return mapper.toDto(discountCard);
    }

    @Override
    public void update(DiscountCardDto card) {
        DiscountCard discountCard = mapper.toEntity(card);
        discountCardDao.update(discountCard);
    }

    @Override
    public void delete(String cardNumber) {
        discountCardDao.delete(cardNumber);
    }
}

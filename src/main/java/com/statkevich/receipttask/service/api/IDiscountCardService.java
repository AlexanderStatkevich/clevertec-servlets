package com.statkevich.receipttask.service.api;

import com.statkevich.receipttask.domain.dto.DiscountCardDto;

public interface IDiscountCardService {

    void save(DiscountCardDto card);

    DiscountCardDto get(String cardNumber);

    void update(DiscountCardDto card);

    void delete(String cardNumber);
}

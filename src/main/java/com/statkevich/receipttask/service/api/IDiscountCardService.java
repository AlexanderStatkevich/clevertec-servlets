package com.statkevich.receipttask.service.api;

import com.statkevich.receipttask.domain.dto.DiscountCardDto;
import com.statkevich.receipttask.dto.PageDto;

public interface IDiscountCardService {

    void save(DiscountCardDto card);

    DiscountCardDto get(String cardNumber);

    PageDto<DiscountCardDto> getPage(Long pageNumber, Long pageSize);

    void update(DiscountCardDto card);

    void delete(String cardNumber);
}

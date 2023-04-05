package com.statkevich.receipttask.service.api;

import com.statkevich.receipttask.dto.OrderDto;
import com.statkevich.receipttask.dto.ReceiptDto;

public interface IOrderService {
    ReceiptDto processingOrder(OrderDto orderDTO);
}

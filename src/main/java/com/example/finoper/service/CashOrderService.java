package com.example.finoper.service;

import com.example.finoper.model.dto.CashOrderDto;
import com.example.finoper.model.dto.CashOrderRequestDto;

import java.util.List;

public interface CashOrderService {
    List<CashOrderDto> readCashOrders(Long id);

    void createCashOrder(CashOrderRequestDto cashOrderRequestDto);

}

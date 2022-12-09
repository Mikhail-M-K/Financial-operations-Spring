package com.example.finoper.controller;

import com.example.finoper.model.dto.CashOrderDto;
import com.example.finoper.model.dto.CashOrderRequestDto;
import com.example.finoper.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CashOrderController {

    private final ClientService clientService;

    @Autowired
    public CashOrderController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Получение данных кассовых ордеров по счету клиента
     * @param id идентификатор счета клиента
     * @return данные по кассовым ордерам счета клиента
     */
    @GetMapping(value="/client/accounts/cashorders/{id}")
    public List<CashOrderDto> readCashOrders(@PathVariable(name="id") Long id) {
        return clientService.readCashOrders(id);
    }

    @PostMapping(value="/cashorders")
    public void createCashOrder(@RequestBody CashOrderRequestDto cashOrderRequestDto) {
        clientService.createCashOrder(cashOrderRequestDto);
    }
}

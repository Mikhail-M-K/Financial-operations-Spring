package com.example.finoper.controller;

import com.example.finoper.model.dto.*;
import com.example.finoper.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value="/clients")
    public List<ClientDto> readAll() {
        return clientService.readAll();
    }

    @GetMapping(value="/client/{id}")
    public ClientDto read(@PathVariable(name="id") Long id) {
        return clientService.read(id);
    }

    @GetMapping(value="/client/accounts/{id}")
    public List<ClientAccountDto> readAccount(@PathVariable(name="id") Long id) {
        return clientService.readAccount(id);
    }

    @GetMapping(value="/client/accounts/transactions/{id}")
    public List<TransactionDto> readTransactionsAccount(@PathVariable(name="id") Long id) {
        return clientService.readTransactionsAccount(id);
    }

    @GetMapping(value="/client/accounts/cashorders/{id}")
    public List<CashOrderDto> readCashOrders(@PathVariable(name="id") Long id) {
        return clientService.readCashOrders(id);
    }

    @PostMapping(value="/cashorders")
    public void createCashOrder(@RequestBody CashOrderRequestDto cashOrderRequestDto) {
        clientService.createCashOrder(cashOrderRequestDto);
    }

    @PostMapping(value="/transfer/oneuser")
    public void createTransactionalTransferOfOneUser(
                                @RequestBody TransferOfOneUserRequestDto oneUserRequestDto) {

        clientService.createTransactionalTransferOfOneUser(oneUserRequestDto);
    }

    @PostMapping(value="/transfer")
    public void createTransactionalTransfer(
            @RequestBody TransactionalTransferRequestDto transferRequestDto) {

        clientService.createTransactionalTransfer(transferRequestDto);
    }

}

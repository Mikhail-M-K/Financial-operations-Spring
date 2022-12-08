package com.example.finoper.controller;

import com.example.finoper.model.TypeOrder;
import com.example.finoper.model.dto.CashOrderDto;
import com.example.finoper.model.dto.ClientAccountDto;
import com.example.finoper.model.dto.ClientDto;
import com.example.finoper.model.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.finoper.service.ClientService;

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
    public void createCashOrder(@RequestParam TypeOrder typeOperation,
                                @RequestParam int numberAccount,
                                @RequestParam double sum,
                                @RequestParam String secretWord) {

        clientService.createCashOrder(typeOperation, numberAccount, sum, secretWord);
    }

    @PostMapping(value="/transfer/oneuser")
    public void createTransactionalTransferOfOneUser(
                                @RequestBody@RequestParam int oneAccount,
                                @RequestBody int twoAccount,
                                @RequestBody double sum,
                                @RequestBody String secretWord) {

        clientService.createTransactionalTransferOfOneUser(oneAccount, twoAccount, sum, secretWord);
    }

    @PostMapping(value="/transfer")
    public void createTransactionalTransfer(
            @RequestBody int oneAccount,
            @RequestBody int twoAccount,
            @RequestBody double sum,
            @RequestBody String secretWord) {

        clientService.createTransactionalTransfer(oneAccount, twoAccount, sum, secretWord);
    }

}

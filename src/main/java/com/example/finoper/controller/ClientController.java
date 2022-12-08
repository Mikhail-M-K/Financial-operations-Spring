package com.example.finoper.controller;

import com.example.finoper.model.dto.*;
import com.example.finoper.repos.ClientAccountRepo;
import com.example.finoper.repos.ClientRepo;
import com.example.finoper.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {
    private final ClientService clientService;
    private final ClientRepo clientRepo;
    private final ClientAccountRepo clientAccountRepo;

    @Autowired
    public ClientController(ClientService clientService,
                            ClientRepo clientRepo,
                            ClientAccountRepo clientAccountRepo) {
        this.clientService = clientService;
        this.clientRepo = clientRepo;
        this.clientAccountRepo = clientAccountRepo;
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

    @PostMapping(value="/client")
    public void create(@RequestBody ClientDto clientDto) {

        clientService.create(clientDto);
    }

    @PostMapping(value="/clientaccount")
    public void createClientAccount(@RequestBody ClientAccountRequestDto clientAccountRequestDto) {
        clientService.createClientAccount(clientAccountRequestDto);
    }


}

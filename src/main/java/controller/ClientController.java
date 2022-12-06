package controller;

import model.CashOrder;
import model.Transaction;
import model.TypeOrder;
import model.dto.CashOrderDto;
import model.dto.ClientAccountDto;
import model.dto.ClientDto;
import model.dto.TransactionDto;
import org.springframework.web.bind.annotation.*;
import service.ClientService;

import java.util.List;

public class ClientController {
    private final ClientService clientService;

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
}

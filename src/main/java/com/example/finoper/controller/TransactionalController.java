package com.example.finoper.controller;

import com.example.finoper.model.dto.TransactionDto;
import com.example.finoper.model.dto.TransactionalTransferRequestDto;
import com.example.finoper.service.TransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionalController {

    private final TransactionalService transactionalService;

    @Autowired
    public TransactionalController(TransactionalService transactionalService) {
        this.transactionalService = transactionalService;
    }


    /**
     * Получение данных транзакций по счету клиента
     * @param id идентификатор счета клиента
     * @return данные по транзакциям счета клиента
     */
    @GetMapping(value="/client/accounts/transactions/{id}")
    public List<TransactionDto> readTransactionsAccount(@PathVariable(name="id") Long id) {
        return transactionalService.readTransactionsAccount(id);
    }

    @PostMapping(value="/transfer/oneuser")
    public void createTransactionalTransferOneUser(
            @RequestBody TransactionalTransferRequestDto transferRequestDto) {
        transferRequestDto.setOneUser(true);
        transactionalService.createTransactionalTransfer(transferRequestDto);
    }

    @PostMapping(value="/transfer")
    public void createTransactionalTransfer(
            @RequestBody TransactionalTransferRequestDto transferRequestDto) {
        transferRequestDto.setOneUser(false);
        transactionalService.createTransactionalTransfer(transferRequestDto);
    }
}

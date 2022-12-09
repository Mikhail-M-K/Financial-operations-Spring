package com.example.finoper.service;

import com.example.finoper.model.dto.TransactionDto;
import com.example.finoper.model.dto.TransactionalTransferRequestDto;

import java.util.List;

public interface TransactionalService {

    List<TransactionDto> readTransactionsAccount(Long id);

    void createTransactionalTransfer(TransactionalTransferRequestDto transferRequestDto);
}

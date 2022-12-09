package com.example.finoper.service;

import com.example.finoper.model.dto.TransactionDto;
import com.example.finoper.model.dto.TransactionalTransferRequestDto;
import com.example.finoper.model.dto.TransferOfOneUserRequestDto;

import java.util.List;

public interface TransactionalService {

    List<TransactionDto> readTransactionsAccount(Long id);

    void createTransactionalTransferOfOneUser(TransferOfOneUserRequestDto oneUserRequestDto);

    void createTransactionalTransfer(TransactionalTransferRequestDto transferRequestDto);
}

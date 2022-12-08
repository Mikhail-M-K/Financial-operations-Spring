package com.example.finoper.service;

import com.example.finoper.model.TypeOrder;
import com.example.finoper.model.dto.CashOrderDto;
import com.example.finoper.model.dto.ClientAccountDto;
import com.example.finoper.model.dto.ClientDto;
import com.example.finoper.model.dto.TransactionDto;

import java.util.List;

public interface ClientService {
    List<ClientDto> readAll();

    ClientDto read(Long id);

    List<ClientAccountDto> readAccount(Long id);

    List<TransactionDto> readTransactionsAccount(Long id);


    List<CashOrderDto> readCashOrders(Long id);

    void createCashOrder(TypeOrder typeOperation, int numberAccount, double sum, String secretWord);

    void createTransactionalTransferOfOneUser(int oneAccount, int twoAccount, double sum, String secretWord);

    void createTransactionalTransfer(int oneAccount, int twoAccount, double sum, String secretWord);
}

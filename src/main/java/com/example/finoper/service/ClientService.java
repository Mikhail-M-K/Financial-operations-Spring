package com.example.finoper.service;

import com.example.finoper.model.Client;
import com.example.finoper.model.dto.*;

import java.util.List;

public interface ClientService {
    List<ClientDto> readAll();

    ClientDto read(Long id);

    List<ClientAccountDto> readAccount(Long id);

    List<TransactionDto> readTransactionsAccount(Long id);


    List<CashOrderDto> readCashOrders(Long id);

    void createCashOrder(CashOrderRequestDto cashOrderRequestDto);

    void createTransactionalTransferOfOneUser(TransferOfOneUserRequestDto oneUserRequestDto);

    void createTransactionalTransfer(TransactionalTransferRequestDto transferRequestDto);

    void create(Client client);

    void createClientAccount(ClientAccountRequestDto clientAccountRequestDto);
}

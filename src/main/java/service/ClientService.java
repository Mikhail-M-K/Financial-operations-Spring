package service;

import model.TypeOrder;
import model.dto.CashOrderDto;
import model.dto.ClientAccountDto;
import model.dto.ClientDto;
import model.dto.TransactionDto;

import java.util.List;

public interface ClientService {
    List<ClientDto> readAll();

    ClientDto read(Long id);

    List<ClientAccountDto> readAccount(Long id);

    List<TransactionDto> readTransactionsAccount(Long id);


    List<CashOrderDto> readCashOrders(Long id);

    void createCashOrder(TypeOrder typeOperation, int numberAccount, double sum, String secretWord);
}

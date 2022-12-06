package service;

import exceptions.NoSuchObjectException;
import model.CashOrder;
import model.Client;
import model.ClientAccount;
import model.Transaction;
import model.dto.CashOrderDto;
import model.dto.ClientAccountDto;
import model.dto.ClientDto;
import model.dto.TransactionDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import repos.ClientAccountRepo;
import repos.ClientRepo;

import java.util.List;
import java.util.stream.Collectors;

public class ClientServiceImpl implements ClientService{

    private final ClientRepo clientRepo;

    private final ClientAccountRepo clientAccountRepo;

    private PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepo clientRepo, ClientAccountRepo clientAccountRepo) {
        this.clientRepo = clientRepo;
        this.clientAccountRepo = clientAccountRepo;
    }

    @Transactional
    @Override
    public List<ClientDto> readAll() {
        return clientRepo
                .findAll()
                .stream()
                .map(this::convertToClientDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public ClientDto read(Long id){
        Client client = clientRepo.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("There is no client with ID = " + id + " in Database"));
        return convertToClientDTO(client);
    }

    @Override
    public List<ClientAccountDto> readAccount(Long id) {
        Client client = clientRepo.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("There is no client account with ID = " + id + " in Database"));
       return client.getClientAccount()
               .stream()
               .map(this::convertAccountList)
               .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> readTransactionsAccount(Long id) {
        ClientAccount clientAccount = clientAccountRepo.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("There is no client account with ID = " + id + " in Database"));
        return clientAccount.getTransactionsList()
                .stream()
                .map(this::convertTransactionList)
                .collect(Collectors.toList());
    }

    @Override
    public List<CashOrderDto> readCashOrders(Long id) {
        ClientAccount clientAccountCash = clientAccountRepo.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("There is no client account with ID = " + id + " in Database"));
        return clientAccountCash.getCashOrderList()
                .stream()
                .map(this::convertCashOrderList)
                .collect(Collectors.toList());
    }


    private ClientDto convertToClientDTO(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setSecondName(client.getSecondName());
        clientDto.setName(client.getName());
        clientDto.setPatronymic(client.getPatronymic());
        clientDto.setSecretWord(passwordEncoder.encode(client.getSecretWord()));
        return clientDto;
    }

    private ClientAccountDto convertAccountList(ClientAccount clientAccount) {
        ClientAccountDto clientAccountDto = new ClientAccountDto();
        clientAccountDto.setAccountNumber(clientAccount.getAccountNumber());
        clientAccountDto.setSum(clientAccount.getSum());
        clientAccountDto.setTypeAccount(clientAccount.getTypeAccount());
        clientAccountDto.setOpeningDate(clientAccount.getOpeningDate());
        clientAccountDto.setValidityPeriod(clientAccount.getValidityPeriod());
        return clientAccountDto;
    }

    private TransactionDto convertTransactionList(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setDateOfCreation(transaction.getDateOfCreation());
        transactionDto.setSum(transaction.getSum());
        transactionDto.setType(transaction.getType());
        transactionDto.setClientAccount(transaction.getClientAccount());
        transactionDto.setCashOrder(transaction.getCashOrder());
        transactionDto.setClientAccount(transaction.getClientAccount());
        transactionDto.setResultTransaction(transaction.getResultTransaction());
        return transactionDto;
    }

    private CashOrderDto convertCashOrderList(CashOrder cashOrder) {
        CashOrderDto cashOrderDto = new CashOrderDto();
        cashOrderDto.setType(cashOrder.getType());
        cashOrderDto.setSumTransaction(cashOrder.getSumTransaction());
        cashOrderDto.setClientAccount(cashOrder.getClientAccount());
        cashOrderDto.setExecutionResult(cashOrder.getExecutionResult());
        cashOrderDto.setDataCreate(cashOrder.getDataCreate());
        return cashOrderDto;
    }
}

package com.example.finoper.service;

import com.example.finoper.exceptions.NoSuchObjectException;
import com.example.finoper.model.*;
import com.example.finoper.model.dto.*;
import com.example.finoper.repos.CashOrderRepo;
import com.example.finoper.repos.ClientAccountRepo;
import com.example.finoper.repos.ClientRepo;
import com.example.finoper.repos.TransactionRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepo clientRepo;

    private final ClientAccountRepo clientAccountRepo;

    private final CashOrderRepo cashOrderRepo;

    private final TransactionRepo transactionRepo;

    private PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepo clientRepo, ClientAccountRepo clientAccountRepo, CashOrderRepo cashOrderRepo, TransactionRepo transactionRepo, PasswordEncoder passwordEncoder) {
        this.clientRepo = clientRepo;
        this.clientAccountRepo = clientAccountRepo;
        this.cashOrderRepo = cashOrderRepo;
        this.transactionRepo = transactionRepo;
        this.passwordEncoder = passwordEncoder;
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

    @Transactional
    @Override
    public List<ClientAccountDto> readAccount(Long id) {
        Client client = clientRepo.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("There is no client with ID = " + id + " in Database"));
       return client.getClientAccount()
               .stream()
               .map(this::convertAccount)
               .collect(Collectors.toList());
    }



    @Transactional
    @Override
    public List<CashOrderDto> readCashOrders(Long id) {
        ClientAccount clientAccountCash = clientAccountRepo.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("There is no client account with ID = " + id + " in Database"));
        return clientAccountCash.getCashOrderList()
                .stream()
                .map(this::convertCashOrder)
                .collect(Collectors.toList());
    }

    @Transactional(noRollbackFor = NoSuchObjectException.class)
    @Override
    public void createCashOrder(CashOrderRequestDto cashOrderRequestDto){
        TypeOrder typeOperation = cashOrderRequestDto.getTypeOperation();
        int numberAccount = cashOrderRequestDto.getNumberAccount();
        double sum = cashOrderRequestDto.getSum();
        String secretWord = cashOrderRequestDto.getSecretWord(), statusesTransactionAndCashOrder;
        Transaction transaction = new Transaction();
        CashOrder cashOrder = new CashOrder();
        LocalDateTime date = LocalDateTime.now();
        boolean checkAccount;
        ClientAccount clientAccount = clientAccountRepo.findClientAccountByAccountNumber(numberAccount).orElse(null);
        checkAccount = clientAccount != null;
        switch (typeOperation) {
            case REFILL:
                if (checkAccount) {
                    clientAccount.setSum(clientAccount.getSum() + sum);
                    cashOrder.setClientAccount(clientAccount);
                    transaction.setClientAccount(clientAccount);
                    cashOrder.setExecutionResult("OK");
                    transaction.setResultTransaction("OK");
                    clientAccountRepo.save(clientAccount);
                } else {
                    cashOrder.setExecutionResult("Данный номер счета: " + numberAccount + " отсутствует в базе данных");
                    transaction.setResultTransaction("Данный номер счета: " + numberAccount + " отсутствует в базе данных");
                }
                cashOrder.setType(typeOperation);
                cashOrder.setSumTransaction(sum);
                cashOrder.setDateCreate(date);
                cashOrder = cashOrderRepo.save(cashOrder);
                transaction.setType(TypeTransaction.REFILL);
                transaction.setDateOfCreation(date);
                transaction.setSum(sum);
                transaction.setCashOrder(cashOrder);
                statusesTransactionAndCashOrder = transactionRepo.save(transaction).getResultTransaction();
                if (!statusesTransactionAndCashOrder.equals("OK")) {
                    throw new NoSuchObjectException(statusesTransactionAndCashOrder);
                }
                break;
            case WITHDRAWAL:
                cashOrder.setType(typeOperation);
                if (checkAccount) {
                    if(passwordEncoder.matches(secretWord, clientAccount.getClient().getSecretWord())) {
                        if(clientAccount.getSum() - sum >= 0) {
                            cashOrder.setExecutionResult("OK");
                            clientAccount.setSum(clientAccount.getSum() - sum);
                            clientAccountRepo.save(clientAccount);
                            transaction.setResultTransaction("OK");
                        } else {
                            cashOrder.setExecutionResult("Недостаточно средств");
                            transaction.setResultTransaction("Недостаточно средств");
                        }
                    } else {
                        cashOrder.setExecutionResult("Неверное секретно слово");
                        transaction.setResultTransaction("Неверное секретно слово");
                    }
                    cashOrder.setClientAccount(clientAccount);
                    transaction.setClientAccount(clientAccount);
                    clientAccountRepo.save(clientAccount);
                } else {
                    cashOrder.setExecutionResult("Данный номер счета: " + numberAccount + " отсутствует в базе данных");
                    transaction.setResultTransaction("Данный номер счета: " + numberAccount + " отсутствует в базе данных");
                }
                cashOrder.setSumTransaction(sum);
                cashOrder.setDateCreate(date);
                cashOrder = cashOrderRepo.save(cashOrder);
                transaction.setType(TypeTransaction.WITHDRAWAL);
                transaction.setDateOfCreation(date);
                transaction.setSum(sum);
                transaction.setCashOrder(cashOrder);
                statusesTransactionAndCashOrder = transactionRepo.save(transaction).getResultTransaction();
                if (!statusesTransactionAndCashOrder.equals("OK")) {
                    throw new NoSuchObjectException(statusesTransactionAndCashOrder);
                }
                break;
        }

    }

    @Override
    public void create(ClientDto clientDto) {
        clientRepo.save(clientDtoToClient(clientDto));
    }

    @Override
    public void createClientAccount(ClientAccountRequestDto clientAccountRequestDto) {
        clientAccountRepo.save(clientAccountDtoToClientAccount(clientAccountRequestDto));
    }

    private Client clientDtoToClient (ClientDto clientDto) {
        Client client = new Client();
        client.setSecondName(clientDto.getSecondName());
        client.setName(clientDto.getName());
        client.setPatronymic(clientDto.getPatronymic());
        client.setSecretWord(passwordEncoder.encode(clientDto.getSecretWord()));
        return client;
    }

    private ClientAccount clientAccountDtoToClientAccount (ClientAccountRequestDto clientAccountRequestDto) {
        ClientAccount clientAccount = new ClientAccount();
        clientAccount.setAccountNumber(clientAccountRequestDto.getAccountNumber());
        clientAccount.setSum(clientAccountRequestDto.getSum());
        clientAccount.setClient(clientRepo.findById(clientAccountRequestDto.getIdClient()).get());
        clientAccount.setTypeAccount(clientAccountRequestDto.getTypeAccount());
        clientAccount.setOpeningDate(LocalDateTime.now());
        return clientAccount;
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

    private ClientAccountDto convertAccount(ClientAccount clientAccount) {
        ClientAccountDto clientAccountDto = new ClientAccountDto();
        clientAccountDto.setAccountNumber(clientAccount.getAccountNumber());
        clientAccountDto.setSum(clientAccount.getSum());
        clientAccountDto.setTypeAccount(clientAccount.getTypeAccount());
        clientAccountDto.setOpeningDate(clientAccount.getOpeningDate());
        clientAccountDto.setValidityPeriod(clientAccount.getValidityPeriod());
        return clientAccountDto;
    }



    private CashOrderDto convertCashOrder(CashOrder cashOrder) {
        CashOrderDto cashOrderDto = new CashOrderDto();
        cashOrderDto.setType(cashOrder.getType());
        cashOrderDto.setSumTransaction(cashOrder.getSumTransaction());
        cashOrderDto.setClientAccount(cashOrder.getClientAccount());
        cashOrderDto.setExecutionResult(cashOrder.getExecutionResult());
        cashOrderDto.setDateCreate(cashOrder.getDateCreate());
        return cashOrderDto;
    }
}

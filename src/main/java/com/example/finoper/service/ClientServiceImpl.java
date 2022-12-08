package com.example.finoper.service;

import com.example.finoper.exceptions.NoSuchObjectException;
import com.example.finoper.model.*;
import com.example.finoper.model.dto.CashOrderDto;
import com.example.finoper.model.dto.ClientAccountDto;
import com.example.finoper.model.dto.ClientDto;
import com.example.finoper.model.dto.TransactionDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.finoper.repos.CashOrderRepo;
import com.example.finoper.repos.ClientAccountRepo;
import com.example.finoper.repos.ClientRepo;
import com.example.finoper.repos.TransactionRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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

    @Transactional
    @Override
    public List<CashOrderDto> readCashOrders(Long id) {
        ClientAccount clientAccountCash = clientAccountRepo.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("There is no client account with ID = " + id + " in Database"));
        return clientAccountCash.getCashOrderList()
                .stream()
                .map(this::convertCashOrderList)
                .collect(Collectors.toList());
    }

    @Transactional(noRollbackFor = NoSuchObjectException.class)
    @Override
    public void createCashOrder(TypeOrder typeOperation, int numberAccount, double sum, String secretWord) {
        Transaction transaction = new Transaction();
        CashOrder cashOrder = new CashOrder();
        LocalDate date = LocalDate.now();
        Long idCash;
        boolean checkAccount;
        ClientAccount clientAccount = clientAccountRepo.findClientAccountByAccountNumber(numberAccount)
                        .orElseThrow(() -> new NoSuchObjectException("There is no client account with number account = " + numberAccount + " in Database"));
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
                cashOrder.setDataCreate(date);
                idCash = cashOrderRepo.save(cashOrder).getId();
                transaction.setType(TypeTransaction.REFILL);
                transaction.setDateOfCreation(date);
                transaction.setSum(sum);
                transaction.setCashOrder(cashOrderRepo.getReferenceById(idCash));
                transactionRepo.save(transaction);
                break;
            case WITHDRAWAL:
                cashOrder.setType(typeOperation);
                if (checkAccount) {
                    //if(Objects.equals(passwordEncoder.encode(clientAccount.getClient().getSecretWord()), secretWord)) {
                    if(Objects.equals(clientAccount.getClient().getSecretWord(), passwordEncoder.encode(secretWord))) {
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
                    //clientAccount.setSum(clientAccount.getSum() + sum);
                    cashOrder.setClientAccount(clientAccount);
                    transaction.setClientAccount(clientAccount);
                    cashOrder.setExecutionResult("OK");
                    transaction.setResultTransaction("OK");
                    clientAccountRepo.save(clientAccount);
                } else {
                    cashOrder.setExecutionResult("Данный номер счета: " + numberAccount + " отсутствует в базе данных");
                    transaction.setResultTransaction("Данный номер счета: " + numberAccount + " отсутствует в базе данных");
                }
                cashOrder.setSumTransaction(sum);
                //cashOrder.setClientAccount(clientAccount);
                cashOrder.setDataCreate(date);
                idCash = cashOrderRepo.save(cashOrder).getId();
                transaction.setType(TypeTransaction.WITHDRAWAL);
                transaction.setDateOfCreation(date);
                transaction.setSum(sum);
                //transaction.setClientAccount(clientAccount);
                transaction.setCashOrder(cashOrderRepo.getReferenceById(idCash));
                transactionRepo.save(transaction);
                break;
        }

    }

    @Transactional(noRollbackFor = NoSuchObjectException.class)
    @Override
    public void createTransactionalTransferOfOneUser(int oneAccount, int twoAccount, double sum, String secretWord) {
        Transaction transaction = new Transaction();
        LocalDate date = LocalDate.now();
        boolean checkSecretWord, checkOneAccount, checkTwoAccount;
        ClientAccount clientAccount = clientAccountRepo.findClientAccountByAccountNumber(oneAccount)
                .orElseThrow(() -> new NoSuchObjectException("There is no client account with number account = " + oneAccount + " in Database"));
        ClientAccount clientTwoAccount = clientAccountRepo.findClientAccountByAccountNumber(twoAccount)
                .orElseThrow(() -> new NoSuchObjectException("There is no client account with number account = " + twoAccount + " in Database"));
        checkOneAccount = clientAccount != null;
        checkTwoAccount = clientTwoAccount != null;
        if (checkOneAccount && checkTwoAccount && clientAccount == clientTwoAccount) {
            //if (Objects.equals(passwordEncoder.encode(clientAccount.getClient().getSecretWord()), secretWord)){
            if (Objects.equals(clientAccount.getClient().getSecretWord(), passwordEncoder.encode(secretWord))){
                if(clientAccount.getSum() - sum >= 0) {
                    clientAccount.setSum(clientAccount.getSum() - sum);
                    clientTwoAccount.setSum(clientTwoAccount.getSum() + sum);
                    clientAccountRepo.save(clientAccount);
                    clientAccountRepo.save(clientTwoAccount);
                    transaction.setResultTransaction("OK");
                } else {
                    transaction.setResultTransaction("Недостаточно средств");
                }
            } else {
                transaction.setResultTransaction("Неверное секретное слово");
            }
            transaction.setClientAccount(clientAccount);
            transaction.setClientOrder(clientTwoAccount);

        } else if (checkOneAccount){
            transaction.setResultTransaction("Данный номер счета: " + oneAccount + " отсутствует в базе данных");
        } else if (checkTwoAccount) {
            transaction.setResultTransaction("Данный номер счета: " + twoAccount + " отсутствует в базе данных");
        } else {
            transaction.setResultTransaction("Данные счета: "+ oneAccount + ", " + twoAccount +" не принадлежат одному пользователю");
        }
        transaction.setDateOfCreation(date);
        transaction.setSum(sum);
        transaction.setType(TypeTransaction.TRANSFER);
        transactionRepo.save(transaction);
        /*checkOneAccount = clientAccount != null;
        checkOneAccount ? checkTwoAccount = clientAccount.getClient().getClientAccount().equals(twoAccount) : ; //проверка второго счета
        checkSecretWord = ; //проверка секретного слова

        if (checkAccounts && checkSecretWord){
            //transaction.setDateOfCreation(date);
            //transaction.setSum(sum);
            if(clientAccount.getSum() - sum >= 0) {
                clientAccount.setSum(clientAccount.getSum() - sum);
                clientTwoAccount.setSum(clientTwoAccount.getSum() + sum);
                clientAccountRepo.save(clientAccount);
                clientAccountRepo.save(clientTwoAccount);
                transaction.setResultTransaction("OK");
            } else {
                transaction.setResultTransaction("Недостаточно средств");
            }
            transaction.setType(TypeTransaction.TRANSFER);
            transaction.setClientAccount(clientAccount);
            transaction.setClientOrder(clientTwoAccount);
            transactionRepo.save(transaction);
        } else {
            if (!checkAccounts && !checkSecretWord) {
                transaction.setResultTransaction("Неверные номер счета для перевода и секретное слово");
            } else if (!checkAccounts && checkSecretWord){
                transaction.setResultTransaction("Неверный номер счета для перевода");
            } else {
                transaction.setResultTransaction("Неверное секретное слово");
            }
            transaction.setType(TypeTransaction.TRANSFER);
            transaction.setClientAccount(clientAccount);
            transactionRepo.save(transaction);
        }*/
    }


    @Transactional(noRollbackFor = NoSuchObjectException.class)
    @Override
    public void createTransactionalTransfer(int oneAccount, int twoAccount, double sum, String secretWord) {
        Transaction transaction = new Transaction();
        LocalDate date = LocalDate.now();
        boolean checkSecretWord, checkOneAccount, checkTwoAccount;
        ClientAccount clientAccount = clientAccountRepo.findClientAccountByAccountNumber(oneAccount)
                .orElseThrow(() -> new NoSuchObjectException("There is no client account with number account = " + oneAccount + " in Database"));
        ClientAccount clientTwoAccount = clientAccountRepo.findClientAccountByAccountNumber(twoAccount)
                .orElseThrow(() -> new NoSuchObjectException("There is no client account with number account = " + twoAccount + " in Database"));
        checkOneAccount = clientAccount != null;
        checkTwoAccount = clientTwoAccount != null;
        if (checkOneAccount && checkTwoAccount) {
            //if (Objects.equals(passwordEncoder.encode(clientAccount.getClient().getSecretWord()), secretWord)) {
            if (Objects.equals(clientAccount.getClient().getSecretWord(), passwordEncoder.encode(secretWord))) {
                if (clientAccount.getSum() - sum >= 0) {
                    clientAccount.setSum(clientAccount.getSum() - sum);
                    clientTwoAccount.setSum(clientTwoAccount.getSum() + sum);
                    clientAccountRepo.save(clientAccount);
                    clientAccountRepo.save(clientTwoAccount);
                    transaction.setResultTransaction("OK");
                } else {
                    transaction.setResultTransaction("Недостаточно средств");
                }
            } else {
                transaction.setResultTransaction("Неверное секретное слово");
            }
            transaction.setClientAccount(clientAccount);
            transaction.setClientOrder(clientTwoAccount);

        } else if (checkOneAccount) {
            transaction.setResultTransaction("Данный номер счета: " + oneAccount + " отсутствует в базе данных");
        } else {
            transaction.setResultTransaction("Данный номер счета: " + twoAccount + " отсутствует в базе данных");
        }
        transaction.setDateOfCreation(date);
        transaction.setSum(sum);
        transaction.setType(TypeTransaction.TRANSFER);
        transactionRepo.save(transaction);
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

    private CashOrder cashOrderDtoToCashOrder(CashOrderDto cashOrderDto) {
        CashOrder cashOrderCrt = new CashOrder();
        cashOrderCrt.setType(cashOrderDto.getType());
        cashOrderCrt.setSumTransaction(cashOrderDto.getSumTransaction());
        cashOrderCrt.setExecutionResult(cashOrderDto.getExecutionResult());
        cashOrderCrt.setDataCreate(cashOrderDto.getDataCreate());
        return cashOrderCrt;

    }
}
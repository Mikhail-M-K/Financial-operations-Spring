package com.example.finoper.service;

import com.example.finoper.exceptions.NoSuchObjectException;
import com.example.finoper.model.*;
import com.example.finoper.model.dto.CashOrderDto;
import com.example.finoper.model.dto.CashOrderRequestDto;
import com.example.finoper.repos.CashOrderRepo;
import com.example.finoper.repos.ClientAccountRepo;
import com.example.finoper.repos.TransactionRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CashOrderServiceImp implements CashOrderService{

    private final ClientAccountRepo clientAccountRepo;
    private final CashOrderRepo cashOrderRepo;

    private final TransactionRepo transactionRepo;

    private PasswordEncoder passwordEncoder;

    public CashOrderServiceImp(ClientAccountRepo clientAccountRepo, CashOrderRepo cashOrderRepo, TransactionRepo transactionRepo, PasswordEncoder passwordEncoder) {
        this.clientAccountRepo = clientAccountRepo;
        this.cashOrderRepo = cashOrderRepo;
        this.transactionRepo = transactionRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public List<CashOrderDto> readCashOrders(Long id) {
        ClientAccount clientAccountCash = clientAccountRepo.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("There is no client account with ID = " + id + " in Database"));
        return clientAccountCash.getCashOrder()
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
        String secretWord = cashOrderRequestDto.getSecretWord();
        String statusesTransactionAndCashOrder ="";
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

    private CashOrderDto convertCashOrder(CashOrder cashOrder) {
        CashOrderDto cashOrderDto = new CashOrderDto();
        cashOrderDto.setId(cashOrder.getId());
        cashOrderDto.setType(cashOrder.getType());
        cashOrderDto.setSumTransaction(cashOrder.getSumTransaction());
        cashOrderDto.setClientAccountId(cashOrder.getClientAccount().getId());
        cashOrderDto.setExecutionResult(cashOrder.getExecutionResult());
        cashOrderDto.setDateCreate(cashOrder.getDateCreate());
        return cashOrderDto;
    }
}

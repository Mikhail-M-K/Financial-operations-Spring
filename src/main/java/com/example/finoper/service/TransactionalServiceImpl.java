package com.example.finoper.service;

import com.example.finoper.exceptions.NoSuchObjectException;
import com.example.finoper.model.ClientAccount;
import com.example.finoper.model.Transaction;
import com.example.finoper.model.TypeTransaction;
import com.example.finoper.model.dto.TransactionDto;
import com.example.finoper.model.dto.TransactionalTransferRequestDto;
import com.example.finoper.repos.ClientAccountRepo;
import com.example.finoper.repos.TransactionRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TransactionalServiceImpl implements TransactionalService{

    private final ClientAccountRepo clientAccountRepo;

    private final TransactionRepo transactionRepo;

    private PasswordEncoder passwordEncoder;

    public TransactionalServiceImpl(ClientAccountRepo clientAccountRepo, TransactionRepo transactionRepo, PasswordEncoder passwordEncoder) {
        this.clientAccountRepo = clientAccountRepo;
        this.transactionRepo = transactionRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<TransactionDto> readTransactionsAccount(Long id) {
        ClientAccount clientAccount = clientAccountRepo.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("There is no client account with ID = " + id + " in Database"));
        return clientAccount.getTransactions()
                .stream()
                .map(this::convertTransaction)
                .collect(Collectors.toList());
    }

    @Transactional(noRollbackFor = NoSuchObjectException.class)
    @Override
    public void createTransactionalTransfer(TransactionalTransferRequestDto transferRequestDto) {
        int firstNumberAccount = transferRequestDto.getFirstNumberAccount();
        int secondNumberAccount = transferRequestDto.getSecondNumberAccount();
        double sum = transferRequestDto.getSum();
        String secretWord = transferRequestDto.getSecretWord();
        Transaction transaction = new Transaction();
        LocalDateTime date = LocalDateTime.now();
        boolean isOneUser = transferRequestDto.getOneUser();
        ClientAccount firstClientAccount = clientAccountRepo.findClientAccountByAccountNumber(firstNumberAccount)
                .orElse(null);
        ClientAccount secondClientAccount = clientAccountRepo.findClientAccountByAccountNumber(secondNumberAccount)
                .orElse(null);
        boolean checkOneAccount = firstClientAccount != null;
        boolean checkTwoAccount = secondClientAccount != null;

        if (checkOneAccount && checkTwoAccount && (isOneUser? Objects.equals(firstClientAccount.getClient().getId(), secondClientAccount.getClient().getId()) : true)) {
            System.out.println(secretWord);
            if (firstNumberAccount != secondNumberAccount) {
                if (passwordEncoder.matches(secretWord, firstClientAccount.getClient().getSecretWord())) {
                    if (firstClientAccount.getSum() - sum >= 0) {
                        firstClientAccount.setSum(firstClientAccount.getSum() - sum);
                        secondClientAccount.setSum(secondClientAccount.getSum() + sum);
                        clientAccountRepo.save(firstClientAccount);
                        clientAccountRepo.save(secondClientAccount);
                        transaction.setResultTransaction("OK");
                    } else {
                        transaction.setResultTransaction("Недостаточно средств");
                    }
                } else {
                    transaction.setResultTransaction("Неверное секретное слово");
                }
            } else {
                transaction.setResultTransaction("Номера счетов совпадают");
            }

        } else if (!checkTwoAccount && !checkOneAccount) {
            transaction.setResultTransaction("Данные счета: " +  firstNumberAccount + ", " + secondNumberAccount +" отсутствует в базе данных");
        } else if (!checkTwoAccount) {
            transaction.setResultTransaction("Данный номер счета: " + secondNumberAccount + " отсутствует в базе данных");
        } else if (!checkOneAccount){

            transaction.setResultTransaction("Данный номер счета: " + firstNumberAccount + " отсутствует в базе данных");
        } else {

            transaction.setResultTransaction("Данные счета: " + firstNumberAccount + ", " + secondNumberAccount +" не принадлежат одному пользователю");
        }
        transaction.setClientAccount(firstClientAccount);
        transaction.setClientOrder(secondClientAccount);
        transaction.setDateOfCreation(date);
        transaction.setSum(sum);
        transaction.setType(TypeTransaction.TRANSFER);
        String statusTransaction = transactionRepo.save(transaction).getResultTransaction();
        if (!statusTransaction.equals("OK")) {
            throw new NoSuchObjectException(statusTransaction);
        }

    }
    private TransactionDto convertTransaction(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setDateOfCreation(transaction.getDateOfCreation());
        transactionDto.setSum(transaction.getSum());
        transactionDto.setType(transaction.getType());
        transactionDto.setClientAccountId(transaction.getClientAccount().getId());
        if (transaction.getType().equals("REFILL") || transaction.getType().equals("WITHDRAWAL")) {
            transactionDto.setCashOrderId(transaction.getCashOrder().getId());
        }
        if (transaction.getType().equals("TRANSFER")) {
            transactionDto.setClientOrderId(transaction.getClientOrder().getId());
        }
        transactionDto.setResultTransaction(transaction.getResultTransaction());
        return transactionDto;
    }
}

package com.example.finoper.service;

import com.example.finoper.exceptions.NoSuchObjectException;
import com.example.finoper.model.ClientAccount;
import com.example.finoper.model.Transaction;
import com.example.finoper.model.TypeTransaction;
import com.example.finoper.model.dto.TransactionDto;
import com.example.finoper.model.dto.TransactionalTransferRequestDto;
import com.example.finoper.model.dto.TransferOfOneUserRequestDto;
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

    public TransactionalServiceImpl(ClientAccountRepo clientAccountRepo, TransactionRepo transactionRepo) {
        this.clientAccountRepo = clientAccountRepo;
        this.transactionRepo = transactionRepo;
    }

    @Override
    public List<TransactionDto> readTransactionsAccount(Long id) {
        ClientAccount clientAccount = clientAccountRepo.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("There is no client account with ID = " + id + " in Database"));
        return clientAccount.getTransactionsList()
                .stream()
                .map(this::convertTransaction)
                .collect(Collectors.toList());
    }


    @Transactional(noRollbackFor = NoSuchObjectException.class)
    @Override
    public void createTransactionalTransferOfOneUser(TransferOfOneUserRequestDto oneUserRequestDto) {
        int oneAccount = oneUserRequestDto.getOneAccount();
        int twoAccount = oneUserRequestDto.getTwoAccount();
        double sum = oneUserRequestDto.getSum();
        String secretWord = oneUserRequestDto.getSecretWord(), statusTransaction;
        Transaction transaction = new Transaction();
        LocalDateTime date = LocalDateTime.now();
        ClientAccount clientAccount = clientAccountRepo.findClientAccountByAccountNumber(oneAccount)
                .orElse(null);
        ClientAccount clientTwoAccount = clientAccountRepo.findClientAccountByAccountNumber(twoAccount)
                .orElse(null);
        //boolean checkOneAccount = clientAccount.getClient() != null;
        //boolean checkTwoAccount = clientTwoAccount.getClient() != null;
        boolean checkOneAccount = clientAccount != null;
        boolean checkTwoAccount = clientTwoAccount != null;
        if (checkOneAccount && checkTwoAccount && Objects.equals(clientAccount.getClient().getId(), clientTwoAccount.getClient().getId())) {
            if (oneAccount != twoAccount) {
                if (passwordEncoder.matches(secretWord, clientAccount.getClient().getSecretWord())) {
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
            } else {
                transaction.setResultTransaction("Номера счетов совпадают");
            }

        } else if (!checkTwoAccount && !checkOneAccount) {
            transaction.setResultTransaction("Данные счета: " +  oneAccount + ", " + twoAccount +" отсутствует в базе данных");
        } else if (!checkTwoAccount) {
            transaction.setResultTransaction("Данный номер счета: " + twoAccount + " отсутствует в базе данных");
        } else if (!checkOneAccount){
            transaction.setResultTransaction("Данный номер счета: " + oneAccount + " отсутствует в базе данных");
        }  else {
            transaction.setResultTransaction("Данные счета: " + oneAccount + ", " + twoAccount +" не принадлежат одному пользователю");
        }
        transaction.setDateOfCreation(date);
        transaction.setSum(sum);
        transaction.setType(TypeTransaction.TRANSFER);
        statusTransaction = transactionRepo.save(transaction).getResultTransaction();
        if (!statusTransaction.equals("OK")) {
            throw new NoSuchObjectException(statusTransaction);
        }


    }


    @Transactional(noRollbackFor = NoSuchObjectException.class)
    @Override
    public void createTransactionalTransfer(TransactionalTransferRequestDto transferRequestDto) {
        int oneAccount = transferRequestDto.getOneAccount();
        int twoAccount = transferRequestDto.getTwoAccount();
        double sum = transferRequestDto.getSum();
        String secretWord = transferRequestDto.getSecretWord(), statusTransaction;
        Transaction transaction = new Transaction();
        LocalDateTime date = LocalDateTime.now();
        ClientAccount clientAccount = clientAccountRepo.findClientAccountByAccountNumber(oneAccount)
                .orElse(null);
        ClientAccount clientTwoAccount = clientAccountRepo.findClientAccountByAccountNumber(twoAccount)
                .orElse(null);
        //boolean checkOneAccount = clientAccount.getClient() != null;
        //boolean checkTwoAccount = clientTwoAccount.getClient() != null;
        boolean checkOneAccount = clientAccount != null;
        boolean checkTwoAccount = clientTwoAccount != null;
        if (checkOneAccount && checkTwoAccount) {
            if (oneAccount != twoAccount) {
                if (passwordEncoder.matches(secretWord, clientAccount.getClient().getSecretWord())) {
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
            }else {
                transaction.setResultTransaction("Номера счетов совпадают");
            }

        } else if (!checkOneAccount && !checkTwoAccount) {
            transaction.setResultTransaction("Данные счета: "+ oneAccount + ", " + twoAccount +" отсутствуют в базе данных");
        } else if (!checkTwoAccount) {
            transaction.setResultTransaction("Данный номер счета: " + twoAccount + " отсутствует в базе данных");
        } else {
            transaction.setResultTransaction("Данный номер счета: " + oneAccount + " отсутствует в базе данных");
        }
        transaction.setDateOfCreation(date);
        transaction.setSum(sum);
        transaction.setType(TypeTransaction.TRANSFER);
        statusTransaction = transactionRepo.save(transaction).getResultTransaction();
        if (!statusTransaction.equals("OK")) {
            throw new NoSuchObjectException(statusTransaction);
        }
    }

    private TransactionDto convertTransaction(Transaction transaction) {
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
}

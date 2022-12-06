package service;

import exceptions.NoSuchObjectException;
import model.*;
import model.dto.CashOrderDto;
import model.dto.ClientAccountDto;
import model.dto.ClientDto;
import model.dto.TransactionDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import repos.CashOrderRepo;
import repos.ClientAccountRepo;
import repos.ClientRepo;
import repos.TransactionRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

public class ClientServiceImpl implements ClientService{

    private final ClientRepo clientRepo;

    private final ClientAccountRepo clientAccountRepo;

    private final CashOrderRepo cashOrderRepo;

    private final TransactionRepo transactionRepo;

    private PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepo clientRepo, ClientAccountRepo clientAccountRepo, CashOrderRepo cashOrderRepo, TransactionRepo transactionRepo) {
        this.clientRepo = clientRepo;
        this.clientAccountRepo = clientAccountRepo;
        this.cashOrderRepo = cashOrderRepo;
        this.transactionRepo = transactionRepo;
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

    @Override
    public List<CashOrderDto> readCashOrders(Long id) {
        ClientAccount clientAccountCash = clientAccountRepo.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("There is no client account with ID = " + id + " in Database"));
        return clientAccountCash.getCashOrderList()
                .stream()
                .map(this::convertCashOrderList)
                .collect(Collectors.toList());
    }

    @Override
    public void createCashOrder(TypeOrder typeOperation, int numberAccount, double sum, String secretWord) {
        Transaction transaction = new Transaction();
        CashOrder cashOrder = new CashOrder();
        LocalDate date = LocalDate.now();
        Long idCash;
        ClientAccount clientAccount = clientAccountRepo.findClientAccountByAccountNumber(numberAccount);

        switch (typeOperation) {
            case REFILL:
                clientAccount.setSum(clientAccount.getSum() + sum);
                cashOrder.setType(typeOperation);
                cashOrder.setSumTransaction(sum);
                cashOrder.setClientAccount(clientAccount);
                cashOrder.setExecutionResult("OK");
                cashOrder.setDataCreate(date);
                idCash = cashOrderRepo.save(cashOrder).getId();
                transaction.setType(TypeTransaction.REFILL);
                transaction.setDateOfCreation(date);
                transaction.setSum(sum);
                transaction.setClientAccount(clientAccount);
                transaction.setCashOrder(cashOrderRepo.getReferenceById(idCash));
                transaction.setResultTransaction("OK");
                transactionRepo.save(transaction);
                clientAccountRepo.save(clientAccount);
                break;
            case WITHDRAWAL:
                cashOrder.setType(typeOperation);
                if(Objects.equals(passwordEncoder.encode(clientAccount.getClient().getSecretWord()), secretWord)) {
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
                cashOrder.setSumTransaction(sum);
                cashOrder.setClientAccount(clientAccount);
                cashOrder.setDataCreate(date);
                idCash = cashOrderRepo.save(cashOrder).getId();
                transaction.setType(TypeTransaction.WITHDRAWAL);
                transaction.setDateOfCreation(date);
                transaction.setSum(sum);
                transaction.setClientAccount(clientAccount);
                transaction.setCashOrder(cashOrderRepo.getReferenceById(idCash));
                transactionRepo.save(transaction);
                break;
        }
    }

    /*@Override
    public void createCashOrder(CashOrderDto cashOrderDto) {
    ClientAccount clientAccount = clientAccountRepo.getReferenceById(cashOrderDto.getId());
    CashOrder cashOrderCrt = cashOrderDtoToCashOrder(cashOrderDto);
    cashOrderCrt.setClientAccount(clientAccount);
    cashOrderRepo.save(cashOrderCrt);
    }*/



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

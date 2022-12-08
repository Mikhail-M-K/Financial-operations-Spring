package com.example.finoper.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="client_accounts")
public class ClientAccount {
    @Id
    @Column(name="id")
    @SequenceGenerator(name="clientsIdSeq", sequenceName="clients_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="clients_id", nullable = false)
    private Client client;

    @Column(name="account_number", unique = true)
    private int accountNumber;

    @Column(name="sum")
    private double sum;

    @Column(name="type_account")
    private String typeAccount;

    @Column(name="opening_date")
    private LocalDateTime openingDate;

    @Column(name="validity_period")
    private LocalDateTime validityPeriod;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientAccount")
    private List<Transaction> transactionsList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientAccount")
    private List<CashOrder> cashOrderList;

    public ClientAccount() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDateTime openingDate) {
        this.openingDate = openingDate;
    }

    public LocalDateTime getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(LocalDateTime validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public List<Transaction> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(List<Transaction> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public List<CashOrder> getCashOrderList() {
        return cashOrderList;
    }

    public void setCashOrderList(List<CashOrder> cashOrderList) {
        this.cashOrderList = cashOrderList;
    }
}

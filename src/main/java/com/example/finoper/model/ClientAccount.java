package com.example.finoper.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="client_account")
public class ClientAccount {
    @Id
    @Column(name="id")
    @SequenceGenerator(name="clientsIdSeq", sequenceName="clients_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="clients_id", nullable = false)
    private Client client;

    @Column(name="account_number")
    private int accountNumber;

    @Column(name="sum")
    private double sum;

    @Column(name="type_account")
    private String typeAccount;

    @Column(name="opening_date")
    private Date openingDate;

    @Column(name="validity_period")
    private Date validityPeriod;

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

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public Date getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(Date validityPeriod) {
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

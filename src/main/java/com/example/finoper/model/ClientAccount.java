package com.example.finoper.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="client_accounts")
public class ClientAccount {
    @Id
    @Column(name="id")
    @SequenceGenerator(name="clients_accountIdSeq", sequenceName="clients_account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.AUTO, generator = "clients_accountIdSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="clients_id", nullable = false)
    private Client client;

    @Column(name="account_number", unique = true)
    private int accountNumber;

    @Column(name="sum")
    private Double sum;

    @Column(name="type_account")
    @Enumerated(EnumType.STRING)
    private TypeAccount typeAccount;

    @Column(name="opening_date")
    private LocalDateTime openingDate;

    @Column(name="validity_period")
    private LocalDateTime validityPeriod;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientAccount")
    private Set<Transaction> transactions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientAccount")
    private Set<CashOrder> cashOrder;

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

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public TypeAccount getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(TypeAccount typeAccount) {
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

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Set<CashOrder> getCashOrder() {
        return cashOrder;
    }

    public void setCashOrder(Set<CashOrder> cashOrder) {
        this.cashOrder = cashOrder;
    }
}

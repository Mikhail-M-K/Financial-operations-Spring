package com.example.finoper.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @Column(name="id")
    @SequenceGenerator(name="clientsIdSeq", sequenceName="clients_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="date_of_creation")
    private LocalDateTime dateOfCreation;

    @Column(name="sum")
    private double sum;

    @Column(name="type_transaction")
    @Enumerated
    private TypeTransaction type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_account_id")
    private ClientAccount clientAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_cash_order")
    private CashOrder cashOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_client_account_transfer")
    private ClientAccount clientOrder;

    @Column(name="result_transaction")
    private String resultTransaction;

    public Transaction() {
    }

    public ClientAccount getClientAccount() {
        return clientAccount;
    }

    public void setClientAccount(ClientAccount clientAccount) {
        this.clientAccount = clientAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public TypeTransaction getType() {
        return type;
    }

    public void setType(TypeTransaction type) {
        this.type = type;
    }

    public CashOrder getCashOrder() {
        return cashOrder;
    }

    public void setCashOrder(CashOrder cashOrder) {
        this.cashOrder = cashOrder;
    }

    public String getResultTransaction() {
        return resultTransaction;
    }

    public void setResultTransaction(String resultTransaction) {
        this.resultTransaction = resultTransaction;
    }

    public ClientAccount getClientOrder() {
        return clientOrder;
    }

    public void setClientOrder(ClientAccount clientOrder) {
        this.clientOrder = clientOrder;
    }

}

package com.example.finoper.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="cash_orders")
public class CashOrder {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "cash_orderIdSeq", sequenceName = "cash_order_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cash_orderIdSeq")
    private Long id;

    @Column(name="type_order")
    @Enumerated(EnumType.STRING)
    private TypeOrder type;

    @Column(name="sum_order")
    private Double sumTransaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_account_id")
    private ClientAccount clientAccount;

    @Column(name="execution_result")
    private String executionResult;

    @Column(name="date_create")
    private LocalDateTime dateCreate;

    public CashOrder() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOrder getType() {
        return type;
    }

    public void setType(TypeOrder type) {
        this.type = type;
    }

    public Double getSumTransaction() {
        return sumTransaction;
    }

    public void setSumTransaction(Double sumTransaction) {
        this.sumTransaction = sumTransaction;
    }

    public ClientAccount getClientAccount() {
        return clientAccount;
    }

    public void setClientAccount(ClientAccount clientAccount) {
        this.clientAccount = clientAccount;
    }

    public String getExecutionResult() {
        return executionResult;
    }

    public void setExecutionResult(String executionResult) {
        this.executionResult = executionResult;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }
}

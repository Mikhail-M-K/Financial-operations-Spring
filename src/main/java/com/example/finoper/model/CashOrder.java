package com.example.finoper.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class CashOrder {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "clientsIdSeq", sequenceName = "clients_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientsIdSeq")
    private Long id;

    @Column(name="type_order")
    @Enumerated
    private TypeOrder type;

    @Column(name="sum_order")
    private double sumTransaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_client_account", nullable = false)
    private ClientAccount clientAccount;

    @Column(name="execution_result")
    private String executionResult;

    @Column(name="data_create")
    private LocalDate dataCreate;

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

    public double getSumTransaction() {
        return sumTransaction;
    }

    public void setSumTransaction(double sumTransaction) {
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

    public LocalDate getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(LocalDate dataCreate) {
        this.dataCreate = dataCreate;
    }
}

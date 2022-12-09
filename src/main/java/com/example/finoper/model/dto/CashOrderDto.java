package com.example.finoper.model.dto;

import com.example.finoper.model.ClientAccount;
import com.example.finoper.model.TypeOrder;

import java.time.LocalDateTime;

public class CashOrderDto {

    private Long id;

    private TypeOrder type;

    private Double sumTransaction;

    private ClientAccount clientAccount;

    private String executionResult;

    private LocalDateTime dateCreate;

    public CashOrderDto() {
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

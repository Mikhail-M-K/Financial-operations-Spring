package com.example.finoper.model.dto;

import com.example.finoper.model.ClientAccount;
import com.example.finoper.model.TypeTransaction;

import java.time.LocalDateTime;

public class TransactionDto {

    private Long id;

    private LocalDateTime dateOfCreation;

    private Double sum;

    private TypeTransaction type;

    private Long clientAccountId;

    private Long cashOrderId;

    private Long clientOrderId;

    private String resultTransaction;

    public TransactionDto() {
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

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public TypeTransaction getType() {
        return type;
    }

    public void setType(TypeTransaction type) {
        this.type = type;
    }

    public String getResultTransaction() {
        return resultTransaction;
    }

    public void setResultTransaction(String resultTransaction) {
        this.resultTransaction = resultTransaction;
    }

    public void setClientAccount(ClientAccount clientAccount) {
    }

    public Long getClientAccountId() {
        return clientAccountId;
    }

    public void setClientAccountId(Long clientAccountId) {
        this.clientAccountId = clientAccountId;
    }

    public Long getCashOrderId() {
        return cashOrderId;
    }

    public void setCashOrderId(Long cashOrderId) {
        this.cashOrderId = cashOrderId;
    }

    public Long getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(Long clientOrderId) {
        this.clientOrderId = clientOrderId;
    }
}

package com.example.finoper.model.dto;

import com.example.finoper.model.Client;
import com.example.finoper.model.TypeAccount;

import java.time.LocalDateTime;

public class ClientAccountDto {

    private Long id;

    private Client client;

    private int accountNumber;

    private Double sum;

    private TypeAccount typeAccount;

    private LocalDateTime openingDate;

    private LocalDateTime validityPeriod;

    public ClientAccountDto() {
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
}


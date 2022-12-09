package com.example.finoper.model.dto;

import com.example.finoper.model.TypeAccount;

public class ClientAccountRequestDto {

    private Long idClient;

    private int accountNumber;

    private Double sum;

    private TypeAccount typeAccount;

    public ClientAccountRequestDto() {
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
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
}

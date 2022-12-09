package com.example.finoper.model.dto;

import com.example.finoper.model.TypeOrder;

public class CashOrderRequestDto {
    private TypeOrder typeOperation;
    private Integer numberAccount;
    private Double sum;
    private String secretWord;

    public CashOrderRequestDto() {
    }

    public TypeOrder getTypeOperation() {
        return typeOperation;
    }

    public Integer getNumberAccount() {
        return numberAccount;
    }

    public Double getSum() {
        return sum;
    }

    public String getSecretWord() {
        return secretWord;
    }
}

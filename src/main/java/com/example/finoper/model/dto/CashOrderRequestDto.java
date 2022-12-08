package com.example.finoper.model.dto;

import com.example.finoper.model.TypeOrder;

public class CashOrderRequestDto {
    private TypeOrder typeOperation;
    private int numberAccount;
    private double sum;
    private String secretWord;

    public CashOrderRequestDto() {
    }

    public TypeOrder getTypeOperation() {
        return typeOperation;
    }

    public int getNumberAccount() {
        return numberAccount;
    }

    public double getSum() {
        return sum;
    }

    public String getSecretWord() {
        return secretWord;
    }
}

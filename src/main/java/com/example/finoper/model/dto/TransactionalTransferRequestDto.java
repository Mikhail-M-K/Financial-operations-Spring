package com.example.finoper.model.dto;

public class TransactionalTransferRequestDto {
    private int oneAccount;

    private int twoAccount;

    private double sum;

    private String secretWord;

    public TransactionalTransferRequestDto() {
    }

    public int getOneAccount() {
        return oneAccount;
    }

    public int getTwoAccount() {
        return twoAccount;
    }

    public double getSum() {
        return sum;
    }

    public String getSecretWord() {
        return secretWord;
    }
}

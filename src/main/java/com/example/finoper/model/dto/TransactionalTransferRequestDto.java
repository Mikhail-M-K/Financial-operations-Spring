package com.example.finoper.model.dto;

public class TransactionalTransferRequestDto {
    private int firstNumberAccount;

    private int secondNumberAccount;

    private Double sum;

    private String secretWord;

    private Boolean isOneUser;

    public TransactionalTransferRequestDto() {
    }

    public void setOneUser(Boolean oneUser) {
        isOneUser = oneUser;
    }

    public Boolean getOneUser() {
        return isOneUser;
    }

    public int getFirstNumberAccount() {
        return firstNumberAccount;
    }

    public int getSecondNumberAccount() {
        return secondNumberAccount;
    }

    public Double getSum() {
        return sum;
    }

    public String getSecretWord() {
        return secretWord;
    }
}

package com.example.finoper.model.dto;

import com.example.finoper.model.ClientAccount;

import java.util.List;

public class ClientDto {

    private Long id;

    private String secondName;

    private String name;

    private String patronymic;

    private String secretWord;

    private List<ClientAccount> clientAccount;

    public ClientDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public List<ClientAccount> getClientAccount() {
        return clientAccount;
    }

    public void setClientAccount(List<ClientAccount> clientAccount) {
        this.clientAccount = clientAccount;
    }
}

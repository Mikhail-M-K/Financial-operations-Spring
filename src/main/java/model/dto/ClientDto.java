package model.dto;

import model.ClientAccount;

import java.util.List;

public class ClientDto {

    private Long id;

    private String secondName;

    private Integer name;

    private Boolean patronymic;

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

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public Boolean getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(Boolean patronymic) {
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

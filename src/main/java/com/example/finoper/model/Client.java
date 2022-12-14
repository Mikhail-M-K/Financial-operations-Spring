package com.example.finoper.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="clients")
public class Client {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "clientsIdSeq", sequenceName = "clients_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "clientsIdSeq")
    private Long id;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "name")
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "secret_word")
    private String secretWord;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private Set<ClientAccount> clientAccount;

    public Client() {
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

    public Set<ClientAccount> getClientAccount() {
        return clientAccount;
    }

    public void setClientAccount(Set<ClientAccount> clientAccount) {
        this.clientAccount = clientAccount;
    }
}

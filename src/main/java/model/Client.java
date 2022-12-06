package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Client {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "clientsIdSeq", sequenceName = "clients_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientsIdSeq")
    private Long id;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "name")
    private Integer name;

    @Column(name = "patronymic")
    private Boolean patronymic;

    @Column(name = "secret_word")
    private String secretWord;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private List<ClientAccount> clientAccount;

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

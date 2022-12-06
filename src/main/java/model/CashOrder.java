package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class CashOrder {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "clientsIdSeq", sequenceName = "clients_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientsIdSeq")
    private Long id;

    @Column(name="type_order")
    @Enumerated
    private TypeOrder type;

    @Column(name="sum_order")
    private double sumTransaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_client_account", nullable = false)
    private ClientAccount clientAccount;

    @Column(name="execution_result")
    private double executionResult;

    @Column(name="data_create")
    private Date dataCreate;

    public CashOrder() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOrder getType() {
        return type;
    }

    public void setType(TypeOrder type) {
        this.type = type;
    }

    public double getSumTransaction() {
        return sumTransaction;
    }

    public void setSumTransaction(double sumTransaction) {
        this.sumTransaction = sumTransaction;
    }

    public ClientAccount getClientAccount() {
        return clientAccount;
    }

    public void setClientAccount(ClientAccount clientAccount) {
        this.clientAccount = clientAccount;
    }

    public double getExecutionResult() {
        return executionResult;
    }

    public void setExecutionResult(double executionResult) {
        this.executionResult = executionResult;
    }

    public Date getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(Date dataCreate) {
        this.dataCreate = dataCreate;
    }
}

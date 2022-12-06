package model.dto;

import model.ClientAccount;
import model.TypeOrder;

import java.util.Date;

public class CashOrderDto {

    private Long id;

    private TypeOrder type;

    private double sumTransaction;

    private ClientAccount clientAccount;

    private double executionResult;

    private Date dataCreate;

    public CashOrderDto() {
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

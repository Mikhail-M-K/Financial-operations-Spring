package com.example.finoper.model;

public enum TypeTransaction {
    REFILL("Пополнение"), WITHDRAWAL("Снятие"), TRANSFER("Перевод");

    private final String title;

    TypeTransaction(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}

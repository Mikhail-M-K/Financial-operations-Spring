package model;

public enum TypeOrder {
    REFILL("Пополнение"), WITHDRAWAL("Снятие");

    private final String title;

    TypeOrder(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}

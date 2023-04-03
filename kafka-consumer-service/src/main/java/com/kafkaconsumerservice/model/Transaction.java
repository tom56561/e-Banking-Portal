package com.kafkaconsumerservice.model;

import java.math.BigDecimal;

public class Transaction {
    private String id;
    private BigDecimal amount;
    private String amountCurrency;
    private String accountIban;
    private String valueDate;
    private String description;

    public Transaction(String id, BigDecimal amount, String amountCurrency, String accountIban, String valueDate, String description) {
        this.id = id;
        this.amount = amount;
        this.amountCurrency = amountCurrency;
        this.accountIban = accountIban;
        this.valueDate = valueDate;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getAmountCurrency() {
        return amountCurrency;
    }

    public String getAccountIban() {
        return accountIban;
    }

    public String getValueDate() {
        return valueDate;
    }

    public String getDescription() {
        return description;
    }
}

package com.kafkaconsumerservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private String id;
    private BigDecimal amount;
    private String amountCurrency;
    private String accountIban;
    private Date valueDate;
    private String description;

    @JsonCreator
    public Transaction(
            @JsonProperty("id") String id,
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("amountCurrency") String amountCurrency,
            @JsonProperty("accountIban") String accountIban,
            @JsonProperty("valueDate") Date valueDate,
            @JsonProperty("description") String description
    ) {
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

    public Date getValueDate() {
        return valueDate;
    }

    public String getDescription() {
        return description;
    }
}

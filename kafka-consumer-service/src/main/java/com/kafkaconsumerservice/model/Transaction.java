package com.kafkaconsumerservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private String identityKey;
    private String uniqueIdentifier;
    private BigDecimal amount;
    private String amountCurrency;
    private String accountIban;
    private Date valueDate;
    private String description;

    @JsonCreator
    public Transaction(
            @JsonProperty("identityKey") String identityKey,
            @JsonProperty("uniqueIdentifier") String uniqueIdentifier,
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("amountCurrency") String amountCurrency,
            @JsonProperty("accountIban") String accountIban,
            @JsonProperty("valueDate") Date valueDate,
            @JsonProperty("description") String description
    ) {
        this.identityKey = identityKey;
        this.uniqueIdentifier = uniqueIdentifier;
        this.amount = amount;
        this.amountCurrency = amountCurrency;
        this.accountIban = accountIban;
        this.valueDate = valueDate;
        this.description = description;
    }

    public String getIdentityKey() {
        return identityKey;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
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

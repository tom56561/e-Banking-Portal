package com.kafkaconsumerservice.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ExchangeRateService {

    public BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency) {
        // Implement the logic to fetch the exchange rate from the third-party provider and convert the amount

        return BigDecimal.TEN;
    }
}

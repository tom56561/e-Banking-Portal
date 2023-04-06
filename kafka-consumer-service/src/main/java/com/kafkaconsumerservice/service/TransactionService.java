package com.kafkaconsumerservice.service;

import com.kafkaconsumerservice.model.PagedResponse;
import com.kafkaconsumerservice.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionKafkaConsumer transactionKafkaConsumer;
    @Autowired
    private ExchangeRateService exchangeRateService;

    public TransactionService(TransactionKafkaConsumer transactionKafkaConsumer, ExchangeRateService exchangeRateService) {
        this.transactionKafkaConsumer = transactionKafkaConsumer;
        this.exchangeRateService = exchangeRateService;
    }

    public PagedResponse<Transaction> getTransactions(String identityKey, int month, int year, int size) {
        Pageable pageable = PageRequest.of(0,size);
        List<Transaction> transactions = transactionKafkaConsumer.getTransactions(identityKey);

        // Filter transactions by month and year
        transactions = transactions.stream()
                .filter(transaction -> {
                    LocalDate date = transaction.getValueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return date.getMonthValue() == month && date.getYear() == year;
                })
                .collect(Collectors.toList());

        // Sort transactions by date (descending)
        transactions.sort(Comparator.comparing(Transaction::getValueDate).reversed());

        // Apply pagination
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), transactions.size());
        Page<Transaction> transactionPage = new PageImpl<>(transactions.subList(start, end), pageable, transactions.size());

        BigDecimal totalCredit = BigDecimal.ZERO;
        BigDecimal totalDebit = BigDecimal.ZERO;
        for (Transaction transaction : transactionPage.getContent()) {
            BigDecimal convertedAmount = exchangeRateService.convert(transaction.getAmount(), transaction.getAmountCurrency(), "USD");
            if (convertedAmount.compareTo(BigDecimal.ZERO) > 0) {
                totalCredit = totalCredit.add(convertedAmount);
            } else {
                totalDebit = totalDebit.add(convertedAmount.abs());
            }
        }

        PagedResponse<Transaction> response = new PagedResponse<>(transactionPage.getContent(), transactionPage.getNumber(), transactionPage.getSize(), transactionPage.getTotalElements(), transactionPage.getTotalPages(), totalCredit, totalDebit);

        return response;
    }

}

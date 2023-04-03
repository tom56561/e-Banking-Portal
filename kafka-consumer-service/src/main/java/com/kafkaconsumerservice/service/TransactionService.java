package com.kafkaconsumerservice.service;

import com.kafkaconsumerservice.model.PagedResponse;
import com.kafkaconsumerservice.model.Transaction;
import com.kafkaconsumerservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ExchangeRateService exchangeRateService;

    public PagedResponse<Transaction> getTransactions(String customerId, int month, int year, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactions = transactionRepository.findByCustomerIdAndMonthAndYear(customerId, month, year, pageable);

        BigDecimal totalCredit = BigDecimal.ZERO;
        BigDecimal totalDebit = BigDecimal.ZERO;
        for (Transaction transaction : transactions.getContent()) {
            BigDecimal convertedAmount = exchangeRateService.convert(transaction.getAmount(), transaction.getAmountCurrency(), "USD");
            if (convertedAmount.compareTo(BigDecimal.ZERO) > 0) {
                totalCredit = totalCredit.add(convertedAmount);
            } else {
                totalDebit = totalDebit.add(convertedAmount.abs());
            }
        }

        PagedResponse<Transaction> response = new PagedResponse<>();
        response.setContent(transactions.getContent());
        response.setPage(transactions.getNumber());
        response.setSize(transactions.getSize());
        response.setTotalPages(transactions.getTotalPages());
        response.setTotalElements(transactions.getTotalElements());
        response.setTotalCredit(totalCredit);
        response.setTotalDebit(totalDebit);

        return response;
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}

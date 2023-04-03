package com.kafkaconsumerservice.service;

import com.kafkaconsumerservice.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransactionGenerator {
    @Autowired
    private TransactionProducer transactionProducer;

    public void generateTransactions() {
        Transaction transaction1 = new Transaction(
                "3c04fcce-65fa-4115-9441-2781f6706ca7",
                BigDecimal.valueOf(85),
                "CHF",
                "CH93-0000-0000-0000-0000-0",
                "07-07-2022",
                "Online payment CHF"
        );
        transactionProducer.sendTransaction(transaction1);

        Transaction transaction2 = new Transaction(
                "cddfd78d-5ace-4b52-be73-aac571397f7a",
                BigDecimal.valueOf(100),
                "GBP",
                "CH93-0000-0000-0000-0000-0",
                "08-07-2022",
                "Online payment GBP"
        );
        transactionProducer.sendTransaction(transaction2);

        // Add more transactions as needed
    }
}

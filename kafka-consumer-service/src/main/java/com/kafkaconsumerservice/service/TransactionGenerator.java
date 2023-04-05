package com.kafkaconsumerservice.service;

import com.kafkaconsumerservice.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TransactionGenerator {
    @Autowired
    private TransactionProducer transactionProducer;

    public TransactionGenerator(TransactionProducer transactionProducer) {
        this.transactionProducer = transactionProducer;
    }

    public void generateTransactions() throws ParseException {

        String dateString1 = "01-03-2023";
        String dateString2 = "02-03-2023";

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = dateFormat.parse(dateString1);
        Date date2 = dateFormat.parse(dateString2);


        Transaction transaction1 = new Transaction(
                "89d3o179-blbc-465b-o9ee-e2d5f6ofEld46",
                BigDecimal.valueOf(150),
                "CHF",
                "CH93-0000-0000-0000-0000-0",
                date1,
                "Online payment CHF"
        );
        transactionProducer.sendTransaction(transaction1);

        Transaction transaction2 = new Transaction(
                "92d43cde-4s2o-5123-1521-523lsd018",
                BigDecimal.valueOf(100),
                "USD",
                "CH93-0000-0000-0000-0000-0",
                date2,
                "Online payment USD"
        );
        transactionProducer.sendTransaction(transaction2);

    }
}

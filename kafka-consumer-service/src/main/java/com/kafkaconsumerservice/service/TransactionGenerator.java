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
        String dateString3 = "03-03-2023";
        String dateString4 = "04-03-2023";
        String dateString5 = "05-03-2023";
        String dateString6 = "06-03-2023";
        String dateString7 = "07-03-2023";
        String dateString8 = "08-03-2023";
        String dateString9 = "09-03-2023";
        String dateString10 = "11-03-2023";
        String dateString11 = "12-03-2023";
        String dateString12 = "02-04-2023";



        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = dateFormat.parse(dateString1);
        Date date2 = dateFormat.parse(dateString2);
        Date date3 = dateFormat.parse(dateString3);
        Date date4 = dateFormat.parse(dateString4);
        Date date5 = dateFormat.parse(dateString5);
        Date date6 = dateFormat.parse(dateString6);
        Date date7 = dateFormat.parse(dateString7);
        Date date8 = dateFormat.parse(dateString8);
        Date date9 = dateFormat.parse(dateString9);
        Date date10 = dateFormat.parse(dateString10);
        Date date11 = dateFormat.parse(dateString11);
        Date date12 = dateFormat.parse(dateString12);



        Transaction transaction1 = new Transaction(
                "P-0123456789",
                "89d3o179-blbc-465b-o9ee-e2d5f6ofEld46",
                BigDecimal.valueOf(150),
                "CHF",
                "CH93-0000-0000-0000-0000-0",
                date1,
                "Online payment CHF"
        );
        Transaction transaction2 = new Transaction(
                "P-0123456789",
                "92d43cde-4s2o-5123-1521-523lsd018",
                BigDecimal.valueOf(100),
                "USD",
                "CH93-0000-0000-0000-0000-0",
                date2,
                "Online payment USD"
        );
        Transaction transaction3 = new Transaction(
                "P-0123456789",
                "67a7p739-ns0f-811a-u1dd-f3e7q3qrTlp21",
                BigDecimal.valueOf(150),
                "USD",
                "CH93-0000-0000-0000-0000-0",
                date3,
                "Online payment USD"
        );
        Transaction transaction4 = new Transaction(
                "P-0123456789",
                "34c6d859-0b8t-394c-a1cc-f1d9e9lkMgd47",
                BigDecimal.valueOf(150),
                "USD",
                "CH93-0000-0000-0000-0000-0",
                date4,
                "Online payment USD"
        );
        Transaction transaction5 = new Transaction(
                "P-0123456789",
                "29s2f584-is7k-622z-e4dd-b4g6t4pgNdk9",
                BigDecimal.valueOf(150),
                "USD",
                "CH93-0000-0000-0000-0000-0",
                date5,
                "Online payment UDS"
        );
        Transaction transaction6 = new Transaction(
                "P-0123456789",
                "16f7j649-mn0p-819z-q8bb-e5r7y5leMng48",
                BigDecimal.valueOf(50),
                "JPY",
                "CH93-0000-0000-0000-0000-0",
                date6,
                "Online payment JPY"
        );
        Transaction transaction7 = new Transaction(
                "P-0123456789",
                "53t9r283-fg5l-092w-u2dd-g2h8i6liQsd76",
                BigDecimal.valueOf(50),
                "JPY",
                "CH93-0000-0000-0000-0000-0",
                date7,
                "Online payment JPY"
        );
        Transaction transaction8 = new Transaction(
                "P-0123456789",
                "83a5d938-vn6t-503g-p1dd-h3j5q3jrMlk3",
                BigDecimal.valueOf(50),
                "JPY",
                "CH93-0000-0000-0000-0000-0",
                date8,
                "Online payment JPY"
        );
        Transaction transaction9 = new Transaction(
                "P-1111111111",
                "72e3q890-kb7s-811z-a2cc-b2k4t8klRfh45",
                BigDecimal.valueOf(20),
                "JPY",
                "CH93-0000-0000-0000-0000-1",
                date9,
                "Online payment JPY"
        );
        Transaction transaction10 = new Transaction(
                "P-1111111111",
                "41b4c072-fm8n-029x-r4dd-f4s7e1heTwp92",
                BigDecimal.valueOf(50),
                "JPY",
                "CH93-0000-0000-0000-0000-1",
                date10,
                "Online payment JPY"
        );
        Transaction transaction11 = new Transaction(
                "P-1111111111",
                "27t8p661-ks1m-627c-t5bb-g6r2f1joYqh88",
                BigDecimal.valueOf(50),
                "JPY",
                "CH93-0000-0000-0000-0000-1",
                date11,
                "Online payment JPY"
        );
        Transaction transaction12 = new Transaction(
                "P-0123456789",
                "65r2f579-ms0n-183g-s9bb-j1h9g6elNfd29",
                BigDecimal.valueOf(-60),
                "USD",
                "CH93-0000-0000-0000-0000-0",
                date12,
                "Online payment USD"
        );

        transactionProducer.sendTransaction(transaction1);
        transactionProducer.sendTransaction(transaction2);
        transactionProducer.sendTransaction(transaction3);
        transactionProducer.sendTransaction(transaction4);
        transactionProducer.sendTransaction(transaction5);
        transactionProducer.sendTransaction(transaction6);
        transactionProducer.sendTransaction(transaction7);
        transactionProducer.sendTransaction(transaction8);
        transactionProducer.sendTransaction(transaction9);
        transactionProducer.sendTransaction(transaction10);
        transactionProducer.sendTransaction(transaction11);
        transactionProducer.sendTransaction(transaction12);

    }
}

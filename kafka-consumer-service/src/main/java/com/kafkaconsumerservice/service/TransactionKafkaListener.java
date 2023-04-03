package com.kafkaconsumerservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkaconsumerservice.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionKafkaListener {
    @Autowired
    private TransactionService transactionService;

    @KafkaListener(topics = "transactions", groupId = "transactions-group")
    public void consumeTransaction(String transactionJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Transaction transaction = objectMapper.readValue(transactionJson, Transaction.class);
            transactionService.saveTransaction(transaction);
        } catch (JsonProcessingException e) {
            // Handle the JSON parsing exception
            e.printStackTrace();
        }
    }
}

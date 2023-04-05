package com.kafkaconsumerservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkaconsumerservice.model.Transaction;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TransactionKafkaConsumer {
    private final ConcurrentHashMap<String, CopyOnWriteArrayList<Transaction>> transactions;
    private final ObjectMapper objectMapper;
    public TransactionKafkaConsumer() {
        this.transactions = new ConcurrentHashMap<>();
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "transactions", groupId = "transactions-group")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            // Deserialize transaction from JSON
            Transaction transaction = objectMapper.readValue(record.value(), Transaction.class);
            saveTransaction(transaction);
        } catch (Exception e) {
            // Handle the JSON parsing exception
            e.printStackTrace();
        }
    }

    private void saveTransaction(Transaction transaction) {
        String identityKey = transaction.getIdentityKey();
        transactions.computeIfAbsent(identityKey, k -> new CopyOnWriteArrayList<>()).add(transaction);
    }

    public List<Transaction> getTransactions(String identityKey) {
        return transactions.getOrDefault(identityKey, new CopyOnWriteArrayList<>());
    }
}
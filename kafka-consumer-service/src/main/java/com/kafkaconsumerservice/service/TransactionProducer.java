package com.kafkaconsumerservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafkaconsumerservice.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC_NAME = "transactions";

    public TransactionProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransaction(Transaction transaction) {
        String key = transaction.getIdentityKey();
        String value = toJson(transaction);
        kafkaTemplate.send(TOPIC_NAME, key, value);
    }

    private String toJson(Transaction transaction) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(transaction);
        } catch (JsonProcessingException e) {
            // Handle the JSON processing exception
            e.printStackTrace();
            return null;
        }
    }
}

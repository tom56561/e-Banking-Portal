package com.kafkaconsumerservice.controller;

import com.kafkaconsumerservice.model.PagedResponse;
import com.kafkaconsumerservice.model.Transaction;
import com.kafkaconsumerservice.service.TransactionGenerator;
import com.kafkaconsumerservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;

@RestController
//@RequestMapping("/api/transactions")
public class TransactionController {
    @GetMapping("/")
    public String home(Principal principal) {
        return "Hello, " + principal.getName();
    }
//    @Autowired
//    private final TransactionService transactionService;
//    @Autowired
//    private final TransactionGenerator transactionGenerator;
//
//    public TransactionController(TransactionService transactionService, TransactionGenerator transactionGenerator) {
//        this.transactionService = transactionService;
//        this.transactionGenerator = transactionGenerator;
//    }

//    @GetMapping
//    public ResponseEntity<PagedResponse<Transaction>> getTransactions(
//            @RequestParam(value = "customerId") String customerId,
//            @RequestParam(value = "month") int month,
//            @RequestParam(value = "year") int year,
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "10") int size) {
//
//        PagedResponse<Transaction> transactions = transactionService.getTransactions(customerId, month, year, page, size);
//        return ResponseEntity.ok(transactions);
//    }

//    @PostMapping("/generate")
//    public ResponseEntity<Void> generateTransactions() throws ParseException {
//        transactionGenerator.generateTransactions();
//        return ResponseEntity.ok().build();
//    }

}


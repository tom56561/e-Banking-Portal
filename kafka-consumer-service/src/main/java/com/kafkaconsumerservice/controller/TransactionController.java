package com.kafkaconsumerservice.controller;

import com.kafkaconsumerservice.model.PagedResponse;
import com.kafkaconsumerservice.model.Transaction;
import com.kafkaconsumerservice.service.TransactionGenerator;
import com.kafkaconsumerservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;
    @Autowired
    private final TransactionGenerator transactionGenerator;

    public TransactionController(TransactionService transactionService, TransactionGenerator transactionGenerator) {
        this.transactionService = transactionService;
        this.transactionGenerator = transactionGenerator;
    }

    @GetMapping("/")
    public String home(Principal principal) {
        return "Hello, " + principal.getName();
    }

    @PostMapping("/mock")
    public ResponseEntity<Void> generateTransactions() throws ParseException {
        transactionGenerator.generateTransactions();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{month}/{year}")
    public ResponseEntity<PagedResponse<Transaction>> getTransactions(
            Authentication authentication,
            @PathVariable(value = "month") int month,
            @PathVariable(value = "year") int year) {

        String identityKey = ((JwtAuthenticationToken) authentication).getToken().getClaim("identity_key");

        PagedResponse<Transaction> transactions = transactionService.getTransactions(identityKey, month, year, 5);
        return ResponseEntity.ok(transactions);
    }
}


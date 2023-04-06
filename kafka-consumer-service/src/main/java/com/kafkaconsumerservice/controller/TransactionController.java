package com.kafkaconsumerservice.controller;

import com.kafkaconsumerservice.model.PagedResponse;
import com.kafkaconsumerservice.model.Transaction;
import com.kafkaconsumerservice.service.TransactionGenerator;
import com.kafkaconsumerservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transaction", description = "Transaction API")
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
    @Operation(summary = "Home endpoint", description = "Returns a welcome message with the user's name")
    public String home(Principal principal) {
        return "Hello, " + principal.getName();
    }

    @PostMapping("/mock")
    @Operation(summary = "Generate mock transactions", description = "Generates mock transactions for testing purposes")
    public ResponseEntity<Void> generateTransactions() throws ParseException {
        transactionGenerator.generateTransactions();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{month}/{year}")
    @Operation(
            summary = "Get transactions by month and year",
            description = "Fetches transactions for the authenticated user by month and year",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(schema = @Schema(implementation = PagedResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    public ResponseEntity<PagedResponse<Transaction>> getTransactions(
            Authentication authentication,
            @Parameter(description = "Month for which transactions are fetched")
            @PathVariable(value = "month") int month,
            @Parameter(description = "Year for which transactions are fetched")
            @PathVariable(value = "year") int year) {

        String identityKey = ((JwtAuthenticationToken) authentication).getToken().getClaim("identity_key");

        PagedResponse<Transaction> transactions = transactionService.getTransactions(identityKey, month, year, 5);
        return ResponseEntity.ok(transactions);
    }
}


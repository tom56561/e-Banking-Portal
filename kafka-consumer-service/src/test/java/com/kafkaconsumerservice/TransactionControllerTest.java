package com.kafkaconsumerservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkaconsumerservice.controller.TransactionController;
import com.kafkaconsumerservice.service.TransactionService;
import com.kafkaconsumerservice.service.TransactionGenerator;
import com.kafkaconsumerservice.model.Transaction;
import com.kafkaconsumerservice.model.PagedResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private TransactionGenerator transactionGenerator;


    @Test
    public void rootWhenUnauthenticatedThen401() throws Exception {
        mockMvc.perform(get("/api/transactions/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void rootWhenAuthenticatedThenSaysHelloUser() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/auth/token")
                        .with(httpBasic("eddie", "1234")))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();

        mockMvc.perform(get("/api/transactions/")
                        .header("Authorization", "Bearer " + token))
                .andExpect(content().string("Hello, eddie"));
    }

    @Test
    @WithMockUser(username = "eddie")
    public void rootWithMockUserStatusIsOK() throws Exception {
        mockMvc.perform(get("/api/transactions/")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "eddie")
    public void testHome() throws Exception {
        mockMvc.perform(get("/api/transactions/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, eddie"));
    }


    @Test
    @WithMockUser(username = "eddie")
    public void testGenerateTransactions() throws Exception {
        mockMvc.perform(post("/api/transactions/mock")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTransactionsWhenUnauthenticated() throws Exception {
        int month = 3;
        int year = 2023;

        mockMvc.perform(get("/api/transactions/{month}/{year}", month, year)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "eddie")
    public void testGetTransactionsWhenAuthenticated() throws Exception {
        int month = 03;
        int year = 2023;
        String dateString1 = "01-03-2023";
        String dateString2 = "02-03-2023";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = dateFormat.parse(dateString1);
        Date date2 = dateFormat.parse(dateString2);
        List<Transaction> transactions = Arrays.asList(
                new Transaction(
                        "P-0123456789",
                        "89d3o179-blbc-465b-o9ee-e2d5f6ofEld46",
                        BigDecimal.valueOf(150),
                        "CHF",
                        "CH93-0000-0000-0000-0000-0",
                        date1,
                        "Online payment CHF"
                ),
                new Transaction(
                        "P-0123456789",
                        "92d43cde-4s2o-5123-1521-523lsd018",
                        BigDecimal.valueOf(100),
                        "USD",
                        "CH93-0000-0000-0000-0000-0",
                        date2,
                        "Online payment USD"
                )
        );


        Pageable pageable = PageRequest.of(0,5);

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), transactions.size());
        Page<Transaction> transactionPage = new PageImpl<>(transactions.subList(start, end), pageable, transactions.size());

        PagedResponse<Transaction> pagedResponse = new PagedResponse<>(transactionPage.getContent(), transactionPage.getNumber(), transactionPage.getSize(), transactionPage.getTotalElements(), transactionPage.getTotalPages(), BigDecimal.ZERO, BigDecimal.ZERO);
        when(transactionService.getTransactions(anyString(), eq(month), eq(year), anyInt(), anyInt())).thenReturn(pagedResponse);

        mockMvc.perform(get("/api/transactions/{month}/{year}", month, year)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(pagedResponse)));
    }
}
package com.kafkaconsumerservice.repository;

import com.kafkaconsumerservice.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query("SELECT t FROM Transaction t WHERE t.customerId = :customerId AND MONTH(t.valueDate) = :month AND YEAR(t.valueDate) = :year")
    Page<Transaction> findByCustomerIdAndMonthAndYear(@Param("customerId") String customerId, @Param("month") int month, @Param("year") int year, Pageable pageable);
}

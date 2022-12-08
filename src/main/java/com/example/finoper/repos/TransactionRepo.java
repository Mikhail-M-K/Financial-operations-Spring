package com.example.finoper.repos;

import com.example.finoper.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository <Transaction, Long> {
}

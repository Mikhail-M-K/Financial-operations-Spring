package com.example.finoper.repos;

import com.example.finoper.model.CashOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashOrderRepo extends JpaRepository<CashOrder, Long> {
}

package com.example.finoper.repos;

import com.example.finoper.model.CashOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashOrderRepo extends JpaRepository<CashOrder, Long> {
}

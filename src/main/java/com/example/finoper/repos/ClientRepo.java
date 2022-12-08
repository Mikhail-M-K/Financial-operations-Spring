package com.example.finoper.repos;

import com.example.finoper.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo  extends JpaRepository<Client, Long> {
}

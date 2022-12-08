package com.example.finoper.repos;

import com.example.finoper.model.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientAccountRepo  extends JpaRepository <ClientAccount, Long> {
    Optional<ClientAccount> findClientAccountByAccountNumber (int accountNumber);
}

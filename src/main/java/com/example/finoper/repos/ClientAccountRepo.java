package com.example.finoper.repos;

import com.example.finoper.model.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientAccountRepo  extends JpaRepository <ClientAccount, Long> {
    Optional<ClientAccount> findClientAccountByAccountNumber (int accountNumber);
}

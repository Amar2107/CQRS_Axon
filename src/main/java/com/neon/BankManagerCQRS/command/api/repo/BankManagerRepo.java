package com.neon.BankManagerCQRS.command.api.repo;

import com.neon.BankManagerCQRS.command.api.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankManagerRepo extends JpaRepository<BankAccount, String> {
}

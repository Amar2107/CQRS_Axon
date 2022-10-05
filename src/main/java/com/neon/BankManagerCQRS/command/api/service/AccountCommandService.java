package com.neon.BankManagerCQRS.command.api.service;

import com.neon.BankManagerCQRS.command.api.model.BankAccount;
import com.neon.BankManagerCQRS.command.api.model.CreateAccountDTO;
import com.neon.BankManagerCQRS.command.api.model.MoneyTransactionDTO;

import java.util.concurrent.CompletableFuture;

public interface AccountCommandService {

    public CompletableFuture<BankAccount> createAccount(CreateAccountDTO createAccount);

    public CompletableFuture<String> creditMoneyToAccount(String accountId, MoneyTransactionDTO moneyCreditDTO);

    public CompletableFuture<String> debitMoneyFromAccount(String accountId, MoneyTransactionDTO moneyCreditDTO);
}

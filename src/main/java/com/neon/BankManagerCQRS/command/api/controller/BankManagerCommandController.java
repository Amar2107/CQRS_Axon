package com.neon.BankManagerCQRS.command.api.controller;

import com.neon.BankManagerCQRS.command.api.model.BankAccount;
import com.neon.BankManagerCQRS.command.api.model.CreateAccountDTO;
import com.neon.BankManagerCQRS.command.api.model.MoneyTransactionDTO;
import com.neon.BankManagerCQRS.command.api.service.AccountCommandService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class BankManagerCommandController {

    @Autowired
    private AccountCommandService service;

    @PostMapping("/create")
    public CompletableFuture<BankAccount> createAccount(@RequestBody CreateAccountDTO createAccountDTO){
        log.info("CONTROLLER with request {}",createAccountDTO);
        CompletableFuture<BankAccount> response =  service.createAccount(createAccountDTO);
        log.info("CONTROLLER with response {}",response);
        return response;
    }

    @PutMapping(value = "/credit/{accountId}")
    public CompletableFuture<String> creditMoneyToAccount(@PathVariable(value = "accountId") String accountId,
                                                          @RequestBody MoneyTransactionDTO moneyCreditDTO) {
        return service.creditMoneyToAccount(accountId, moneyCreditDTO);
    }

    @PutMapping(value = "/debit/{accountId}")
    public CompletableFuture<String> debitMoneyFromAccount(@PathVariable(value = "accountId") String accountId,
                                                           @RequestBody MoneyTransactionDTO moneyDebitDTO) {
        return service.debitMoneyFromAccount(accountId, moneyDebitDTO);
    }

}

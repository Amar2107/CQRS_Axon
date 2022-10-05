package com.neon.BankManagerCQRS.query.api.controller;

import com.neon.BankManagerCQRS.command.api.model.BankAccount;
import com.neon.BankManagerCQRS.query.api.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/accounts")
public class BankManagerQueryController {

    private final AccountService accountService;

    public BankManagerQueryController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    public CompletableFuture<BankAccount> findAccountByAccountId(@PathVariable("accountId") String accountId)
    {
        return this.accountService.findById(accountId);
    }

    @GetMapping("/{accountId}/events")
    public List<Object> eventBus(@PathVariable(value = "accountId") String accountId)
    {
        return this.accountService.listOfActivityForAccount(accountId);
    }


}

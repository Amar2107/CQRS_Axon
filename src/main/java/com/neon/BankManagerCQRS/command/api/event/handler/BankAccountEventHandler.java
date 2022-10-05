package com.neon.BankManagerCQRS.command.api.event.handler;

import com.neon.BankManagerCQRS.command.api.event.AccountCreatedEvent;
import com.neon.BankManagerCQRS.command.api.event.MoneyCreditedEvent;
import com.neon.BankManagerCQRS.command.api.event.MoneyDebitedEvent;
import com.neon.BankManagerCQRS.command.api.exception.AccountNotFoundException;
import com.neon.BankManagerCQRS.command.api.model.BankAccount;
import com.neon.BankManagerCQRS.command.api.repo.BankManagerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class BankAccountEventHandler {

    private final BankManagerRepo repo;

    @EventHandler
    public void on(AccountCreatedEvent event)
    {
        log.info("Event Handler: Handling a Bank Account creation command {}", event.getId());
        BankAccount account = BankAccount.builder()
                .id(event.getId().toString())
                .balance(event.getInitialBalance())
                .owner(event.getOwner())
                .build();
        repo.save(account);
        log.info("Event Handler: Account {} saved to DB",account.getId());
    }

    @EventHandler
    public void on(MoneyCreditedEvent event) throws AccountNotFoundException {
        log.debug("Handling an Account Credit command {}", event.getId());
        Optional<BankAccount> optionalBankAccount = this.repo.findById(event.getId().toString());
        if (optionalBankAccount.isPresent()) {
            BankAccount bankAccount = optionalBankAccount.get();
            bankAccount.setBalance(bankAccount.getBalance().add(event.getCreditedAmount()));
            this.repo.save(bankAccount);
        } else {
            throw new AccountNotFoundException(event.getId());
        }
    }

    @EventHandler
    public void on(MoneyDebitedEvent event) throws AccountNotFoundException {
        log.debug("Handling an Account Debit command {}", event.getId());
        Optional<BankAccount> optionalBankAccount = this.repo.findById(event.getId().toString());
        if (optionalBankAccount.isPresent()) {
            BankAccount bankAccount = optionalBankAccount.get();
            bankAccount.setBalance(bankAccount.getBalance().subtract(event.getMoneyDebited()));
            this.repo.save(bankAccount);
        } else {
            throw new AccountNotFoundException(event.getId());
        }
    }

}

package com.neon.BankManagerCQRS.command.api.service;

import com.neon.BankManagerCQRS.command.api.command.CreateBankAccountCommand;
import com.neon.BankManagerCQRS.command.api.command.CreditMoneyCommand;
import com.neon.BankManagerCQRS.command.api.command.DebitMoneyCommand;
import com.neon.BankManagerCQRS.command.api.model.BankAccount;
import com.neon.BankManagerCQRS.command.api.model.CreateAccountDTO;
import com.neon.BankManagerCQRS.command.api.model.MoneyTransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AccountCommandServiceImpl implements AccountCommandService{

    private final CommandGateway commandGateway;

    public AccountCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<BankAccount> createAccount(CreateAccountDTO createAccount) {
        log.info("SERVICE createAccount with dto ");
        CreateBankAccountCommand createBankAccountCommand = CreateBankAccountCommand.builder()
                .id(UUID.randomUUID())
                .owner(createAccount.getOwner())
                .initialBalance(createAccount.getInitialBalance())
                .build();
        log.info("SERVICE createAccount before sending the value to commandGateway ");
        return commandGateway.send(createBankAccountCommand);
    }


    public CompletableFuture<String> creditMoneyToAccount(String accountId, MoneyTransactionDTO moneyCreditDTO) {
        UUID myVal = null;
        try {
            myVal = UUID.fromString(accountId);
        }catch (Exception e) {log.info("FAILED IN CONVERSION");}

        return this.commandGateway.send(new CreditMoneyCommand(myVal, moneyCreditDTO.getAmount()));
    }

    public CompletableFuture<String> debitMoneyFromAccount(String accountId,
                                                           MoneyTransactionDTO moneyDebitDTO) {
        return this.commandGateway.send(new DebitMoneyCommand(
                UUID.fromString(accountId),
                moneyDebitDTO.getAmount()
        ));
    }


}

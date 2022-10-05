package com.neon.BankManagerCQRS.command.api.aggregate;

import com.neon.BankManagerCQRS.command.api.command.CreateBankAccountCommand;
import com.neon.BankManagerCQRS.command.api.command.CreditMoneyCommand;
import com.neon.BankManagerCQRS.command.api.command.DebitMoneyCommand;
import com.neon.BankManagerCQRS.command.api.event.AccountCreatedEvent;
import com.neon.BankManagerCQRS.command.api.event.MoneyCreditedEvent;
import com.neon.BankManagerCQRS.command.api.event.MoneyDebitedEvent;
import com.neon.BankManagerCQRS.command.api.exception.InsufficientBalanceException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Aggregate
@Getter
@AllArgsConstructor
public class BankAccountAggregate {

    @AggregateIdentifier
    private UUID id;
    private String owner;
    private BigDecimal balance;

    public BankAccountAggregate() {}

    @CommandHandler
    public BankAccountAggregate(CreateBankAccountCommand command){
        log.info("AGGREGATE CLASS: CREATE AGGREGATE LIFECYCLE set with command value {}",command);
        AggregateLifecycle.apply(
            new AccountCreatedEvent(command.getId(),
                    command.getInitialBalance(),
                    command.getOwner())
        );
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent createdEvent){
        this.id = createdEvent.getId();
        this.balance = createdEvent.getInitialBalance();
        this.owner = createdEvent.getOwner();
        log.info(" AGGREGATE CLASS: EVENT SOURCING HANDLER done createdEvent{}  id{}",createdEvent,this.id);
    }

    @CommandHandler
    public void handle(CreditMoneyCommand command){
        log.info("AGGREGATE CLASS: CREDIT AGGREGATE LIFECYCLE set with command value {}",command);
        AggregateLifecycle.apply(
                new MoneyCreditedEvent(command.getId(),
                        command.getMoneyCredited())
        );
    }

    @EventSourcingHandler
    public void on(MoneyCreditedEvent event){
        this.balance = this.balance.add(event.getCreditedAmount());
        log.info(" AGGREGATE CLASS: EVENT SOURCING HANDLER done money credited{}  id{}",event,this.balance);
    }

    @CommandHandler
    public void handle(DebitMoneyCommand command){
        log.info("AGGREGATE CLASS: DEBIT AGGREGATE LIFECYCLE set with command value {}",command);
        AggregateLifecycle.apply(
                new MoneyDebitedEvent(command.getId(),
                        command.getMoneyDebited())
        );
    }

    @EventSourcingHandler
    public void on(MoneyDebitedEvent event) throws InsufficientBalanceException {

        if (this.balance.compareTo(event.getMoneyDebited()) < 0) {
            throw new InsufficientBalanceException(event.getId(), event.getMoneyDebited());
        }
        this.balance = this.balance.subtract(event.getMoneyDebited());
        log.info(" AGGREGATE CLASS: EVENT SOURCING HANDLER done money debited{}  id{}",event,this.balance);
    }
}

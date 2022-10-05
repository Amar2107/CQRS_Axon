package com.neon.BankManagerCQRS.command.api.command;

import lombok.Data;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class CreditMoneyCommand {
    @TargetAggregateIdentifier
    private final UUID id;
    private final BigDecimal moneyCredited;
}

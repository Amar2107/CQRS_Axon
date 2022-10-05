package com.neon.BankManagerCQRS.command.api.event;

import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class MoneyDebitedEvent {

    private final UUID id;
    private final BigDecimal moneyDebited;

}

package com.neon.BankManagerCQRS.command.api.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CreateAccountDTO {

    private final BigDecimal initialBalance;
    private final String owner;
}

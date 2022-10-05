package com.neon.BankManagerCQRS.command.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class MoneyTransactionDTO {

    private BigDecimal amount;
}

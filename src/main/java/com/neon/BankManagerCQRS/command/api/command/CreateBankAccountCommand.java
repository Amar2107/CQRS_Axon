package com.neon.BankManagerCQRS.command.api.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBankAccountCommand {

    @TargetAggregateIdentifier
    private UUID id;
    private String owner;
    private BigDecimal initialBalance;

}

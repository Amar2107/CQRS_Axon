package com.neon.BankManagerCQRS.query.api.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindBankAccountQuery {
    private UUID accountId;
}

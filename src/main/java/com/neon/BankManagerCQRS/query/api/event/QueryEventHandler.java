package com.neon.BankManagerCQRS.query.api.event;

import com.neon.BankManagerCQRS.command.api.model.BankAccount;
import com.neon.BankManagerCQRS.command.api.repo.BankManagerRepo;
import com.neon.BankManagerCQRS.query.api.query.FindBankAccountQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QueryEventHandler {

    private final BankManagerRepo repo;

    public QueryEventHandler(BankManagerRepo repo) {
        this.repo = repo;
    }

    @QueryHandler
    public BankAccount handle(FindBankAccountQuery query){
    return this.repo.findById(query.getAccountId().toString()).orElse(null);
    }

}

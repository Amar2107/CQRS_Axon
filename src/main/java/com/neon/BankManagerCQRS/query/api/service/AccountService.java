package com.neon.BankManagerCQRS.query.api.service;

import com.neon.BankManagerCQRS.command.api.model.BankAccount;
import com.neon.BankManagerCQRS.query.api.query.FindBankAccountQuery;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final QueryGateway queryGateway;
    private final EventStore eventStore;


    public AccountService(QueryGateway queryGateway, EventStore eventStore) {
        this.queryGateway = queryGateway;
        this.eventStore = eventStore;
    }

    public CompletableFuture<BankAccount> findById(String accountId){
        return this.queryGateway.query(new FindBankAccountQuery(UUID.fromString(accountId))
                ,ResponseTypes.instanceOf(BankAccount.class)
        );
    }

    public List<Object> listOfActivityForAccount(String accountId){
        return this.eventStore.readEvents(accountId)
                .asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList());
    }

}

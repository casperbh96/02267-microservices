package com.dtupay.app;

import com.dtupay.database.ITokenAdapter;

import java.util.UUID;

public interface ITokenManagement {
    public UUID GetToken();
    public void CustomerGetTokens(Customer customer, int numTokens, ITokenAdapter tokens);
    public boolean CanCustomerGetTokens(Customer customer, int numTokens);
}

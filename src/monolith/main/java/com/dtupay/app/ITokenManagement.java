package com.dtupay.app;

import com.dtupay.database.ITokenAdapter;
import com.dtupay.database.exceptions.CustomerIsUnableToReceiveNewTokens;
import com.dtupay.database.exceptions.TooManyTokenRequest;

import java.util.UUID;

public interface ITokenManagement {
    public UUID GetToken();
    public void CustomerGetTokens(Customer customer, int numTokens, ITokenAdapter tokens) throws CustomerIsUnableToReceiveNewTokens, TooManyTokenRequest;
    public boolean CanCustomerGetTokens(Customer customer, int numTokens) throws TooManyTokenRequest;
}

package com.calculator.database;

import com.calculator.Customer;

import java.util.UUID;

public interface ITokenManagement {
    public UUID GetToken();
    public void CustomerGetTokens(Customer customer, int numTokens);
    public boolean CanCustomerGetTokens(Customer customer, int numTokens);
}

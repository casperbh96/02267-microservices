package com.dtupay.junit;

import com.dtupay.app.Customer;
import com.dtupay.app.ITokenManagement;
import com.dtupay.app.Token;
import com.dtupay.app.TokenManagement;
import com.dtupay.database.exceptions.TooManyTokenRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.UUID;

public class TokenManagementTest {

    ITokenManagement tm;
    Customer customer;
    Token token1;

    @Before
    public void Setup(){
    tm = new TokenManagement();
    customer = new Customer(1, "1", "Test");
    token1 = new Token(1, UUID.randomUUID(), 1);
    }

    @Test
    public final void WhenCustomerOnlyHasOneUnusedTokenAndOneUsedTokenThenHeIsValidToGetNewTokens() throws TooManyTokenRequest {
        //Arrange
        ArrayList<Token> t = new ArrayList<>();

        //new used token
        Token t2 = new Token();
        t2.setUuid(UUID.randomUUID());
        t2.setUsed(true);

        t.add(token1);
        t.add(t2);

        customer.setTokens(t);

        //Act
        boolean actual = tm.CanCustomerGetTokens(customer, 3);

        //Assert
        Assert.assertTrue(actual);
    }

    @Test
    public final void WhenCustomerHasTwoUnusedTokenThenHeIsNotValidToGetNewTokens() throws TooManyTokenRequest {
        //Arrange
        ArrayList<Token> t = new ArrayList<>();

        //new unused token
        Token t2 = new Token();
        t2.setUuid(UUID.randomUUID());
        t2.setUsed(false);

        t.add(token1);
        t.add(t2);

        customer.setTokens(t);

        //Act
        boolean actual = tm.CanCustomerGetTokens(customer, 3);

        //Assert
        Assert.assertFalse(actual);
    }

    @Test
    public final void WhenCustomerOnlyHasNoTokensThenHeIsValidToGetNewTokens() throws TooManyTokenRequest {
        //Arrange
        ArrayList<Token> t = new ArrayList<>();
        customer.setTokens(t);

        //Act
        boolean actual = tm.CanCustomerGetTokens(customer, 5);

        //Assert
        Assert.assertTrue(actual);
    }

    @Test(expected = TooManyTokenRequest.class)
    public final void WhenCustomerAskForTooManyTokensHeWillGetAnException() throws Exception{
        ArrayList<Token> tokens = new ArrayList<>();
        customer.setTokens(tokens);

        int numTokens = 6;

        //Act
        tm.CanCustomerGetTokens(customer, numTokens);
    }
}

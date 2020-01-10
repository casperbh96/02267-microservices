package com.dtupay;

import com.dtupay.app.Customer;
import com.dtupay.app.ITokenManagement;
import com.dtupay.app.Token;
import com.dtupay.app.TokenManagement;
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
    customer = new Customer(1, "Test");
    token1 = new Token(UUID.randomUUID(), 1);
    }

    @Test
    public final void WhenCustomerOnlyHasOneUnusedTokenAndOneUsedTokenThenHeIsValidToGetNewTokens(){
        //Arrange
        ArrayList<Token> t = new ArrayList<>();

        //new used token
        Token t2 = new Token();
        t2.setId(UUID.randomUUID());
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
    public final void WhenCustomerHasTwoUnusedTokenThenHeIsNotValidToGetNewTokens(){
        //Arrange
        ArrayList<Token> t = new ArrayList<>();

        //new unused token
        Token t2 = new Token();
        t2.setId(UUID.randomUUID());
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
    public final void WhenCustomerOnlyHasNoTokensThenHeIsValidToGetNewTokens(){
        //Arrange
        ArrayList<Token> t = new ArrayList<>();
        customer.setTokens(t);

        //Act
        boolean actual = tm.CanCustomerGetTokens(customer, 5);

        //Assert
        Assert.assertTrue(actual);
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public final void WhenCustomerAskForTooManyTokensHeWillGetAnException() throws Exception{
        //Arrange
        expectedEx.expect(RuntimeException.class);

        ArrayList<Token> t = new ArrayList<>();
        customer.setTokens(t);

        int numTokens = 6;

        //Act
        expectedEx.expectMessage("Too many token request: " + numTokens);
        tm.CanCustomerGetTokens(customer, numTokens);
    }
}

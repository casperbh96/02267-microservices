/*
package com.token.junit;

import com.dtupay.BusinessLogic.BusinessLogicForCustomer;
import com.dtupay.BusinessLogic.TokenManager;
import com.dtupay.BusinessLogic.IBusinessLogicForCustomer;
import com.dtupay.BusinessLogic.ITokenManager;
import com.dtupay.app.Customer;
import com.dtupay.app.Token;
import com.dtupay.database.exceptions.CustomerHasNoUnusedToken;
import com.dtupay.database.exceptions.TooManyTokenRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

public class BusinessLogicTestForToken {

    IManagerCustomer customerLogic;
    ITokenManager tokenLogic;
    Customer customer;
    Customer customerNoToken;
    Token token;

    @Before
    public void Setup() {
        customerLogic = new BusinessLogicForCustomer();
        tokenLogic = new TokenManager();
        customer = customerLogic.createCustomer("9876", "BLCustomer");
        customerNoToken = customerLogic.createCustomer("211", "BLLCustomer2");
        token = new Token(customer.getId(), UUID.randomUUID(), false);
    }

    @Test
    public void GetUnusedTokenByCustomerIdWithAUnusedToken() throws CustomerHasNoUnusedToken {
        Token newToken = tokenLogic.createToken(customer.getId(), UUID.randomUUID(), false);
        Token actualToken = tokenLogic.getUnusedTokenByCustomerId(customer.getId());
        Assert.assertEquals(newToken.getCustomerId(), actualToken.getCustomerId());
    }

    @Test(expected = CustomerHasNoUnusedToken.class)
    public void GetUnusedTokenByCustomerIdWithNoUnusedToken() throws CustomerHasNoUnusedToken {
        tokenLogic.getUnusedTokenByCustomerId(customerNoToken.getId());
    }

    @Test
    public void CreateATokenAndChecksIfTheTokenHasBeenAdded() {
        Token newToken = tokenLogic.createToken(customer.getId(), UUID.randomUUID(), false);
        Assert.assertTrue(tokenLogic.isTokenValid(newToken.getId()));
    }

    @Test
    public final void WhenCustomerOnlyHasOneUnusedTokenAndOneUsedTokenThenHeIsValidToGetNewTokens() throws TooManyTokenRequest {
        //Arrange
        ArrayList<Token> t = new ArrayList<>();

        //new used token
        Token t2 = new Token();
        t2.setUuid(UUID.randomUUID());
        t2.setUsed(true);

        t.add(token);
        t.add(t2);

        customer.setTokens(t);

        //Act
        boolean actual = tokenLogic.canCustomerGetTokens(customer, 3);

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

        t.add(token);
        t.add(t2);

        customer.setTokens(t);

        //Act
        boolean actual = tokenLogic.canCustomerGetTokens(customer, 3);

        //Assert
        Assert.assertFalse(actual);
    }

    @Test
    public final void WhenCustomerOnlyHasNoTokensThenHeIsValidToGetNewTokens() throws TooManyTokenRequest {
        //Arrange
        ArrayList<Token> t = new ArrayList<>();
        customer.setTokens(t);

        //Act
        boolean actual = tokenLogic.canCustomerGetTokens(customer, 5);

        //Assert
        Assert.assertTrue(actual);
    }

    @Test(expected = TooManyTokenRequest.class)
    public final void WhenCustomerAskForTooManyTokensHeWillGetAnException() throws Exception {
        ArrayList<Token> tokens = new ArrayList<>();
        customer.setTokens(tokens);

        int numTokens = 6;

        //Act
        tokenLogic.canCustomerGetTokens(customer, numTokens);
    }
}


 */
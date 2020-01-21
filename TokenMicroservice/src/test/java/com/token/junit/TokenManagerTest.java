package com.token.junit;

import com.token.app.Customer;
import com.token.app.Token;
import com.token.database.exceptions.CustomerHasNoUnusedToken;
import com.token.manager.ITokenManager;
import com.token.manager.TokenManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class TokenManagerTest {

    ITokenManager tokenLogic;
    Customer customer;
    Customer customerNoToken;
    Token token;

    @Before
    public void Setup() {
        tokenLogic = new TokenManager();
        customer = new Customer(9912341,"9876", "BLCustomer");
        customerNoToken = new Customer(9191235, "211", "BLLCustomer2");
        token = new Token(customer.getId(), UUID.randomUUID(), false);
    }

    @Test
    public void GetUnusedTokenByCustomerIdWithAUnusedToken() throws CustomerHasNoUnusedToken {
        Token actualToken = tokenLogic.getUnusedTokenByCustomerId(customer.getId());
        Assert.assertEquals(token.getCustomerId(), actualToken.getCustomerId());
        Assert.assertFalse(actualToken.getUsed());
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
}
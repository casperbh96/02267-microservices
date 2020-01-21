package com.token.junit;

import com.token.app.Customer;
import com.token.app.Token;
import com.token.database.ITokenAdapter;
import com.token.database.TokenAdapter;
import com.token.database.exceptions.CustomerHasNoUnusedToken;
import com.token.database.exceptions.FakeToken;
import com.token.database.exceptions.TokenAlreadyUsed;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;

/**
 * @author Danial
 *
 */
public class TokenAdapterTest {

    ITokenAdapter tokenAdapter;
    Token token;
    Customer customer;
    Customer customerNoToken;

    @Before
    public void Setup() {
        tokenAdapter = new TokenAdapter();
        customer = new Customer(12345, "1", "Test1");
        customerNoToken = new Customer(12341234, "2", "Test2");
        token = tokenAdapter.createToken(customer.getId(), UUID.randomUUID(), false);
    }

    @Test
    public void GetUnusedTokenByCustomerIdWithGetUnusedToken() throws CustomerHasNoUnusedToken {
        Token actualToken = tokenAdapter.getUnusedTokenByCustomerId(customer.getId());
        Assert.assertEquals(token.getCustomerId(), actualToken.getCustomerId());
        Assert.assertFalse(token.getUsed());
    }

    @Test(expected = CustomerHasNoUnusedToken.class)
    public void GetUnusedTokenByCustomerIdWithNoUnusedToken() throws CustomerHasNoUnusedToken {
        tokenAdapter.getUnusedTokenByCustomerId(customerNoToken.getId());
    }

    @Test
    public void CreateATokenAndChecksIfTheTokenHasBeenAdded() throws FakeToken, TokenAlreadyUsed {
        Token newToken = tokenAdapter.createToken(customer.getId(), UUID.randomUUID(), false);
        Assert.assertTrue(tokenAdapter.isTokenValid(newToken.getId()));
    }
}
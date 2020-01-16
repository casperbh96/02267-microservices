package com.dtupay.junit;

import com.dtupay.app.Customer;
import com.dtupay.app.Token;
import com.dtupay.database.CustomerAdapter;
import com.dtupay.database.ICustomerAdapter;
import com.dtupay.database.ITokenAdapter;
import com.dtupay.database.TokenAdapter;
import com.dtupay.database.exceptions.CustomerHasNoUnusedToken;
import com.dtupay.database.exceptions.FakeToken;
import com.dtupay.database.exceptions.TokenAlreadyUsed;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class TokenAdapterTest {

    ICustomerAdapter customerAdapter;
    ITokenAdapter tokenAdapter;
    Token token;
    Customer customer;
    int customerId = 99;

    @Before
    public void Setup() {
        customerAdapter = new CustomerAdapter();
        tokenAdapter = new TokenAdapter();
        token = new Token(UUID.randomUUID(), customerId);
        customer = new Customer(customerId, "Test");
    }

    @Test
    public void GetUnusedTokenByCustomerIdWithAUnusedToken() throws CustomerHasNoUnusedToken {
        CreateCustomer(false);
        Token actualToken = tokenAdapter.getUnusedTokenByCustomerId(customerId);
        Assert.assertEquals(token, actualToken);
    }

    @Test(expected = CustomerHasNoUnusedToken.class)
    public void GetUnusedTokenByCustomerIdWithNoUnusedToken() throws CustomerHasNoUnusedToken {
        CreateCustomer(true);
        tokenAdapter.getUnusedTokenByCustomerId(customerId);
    }

    @Test
    public void CreateATokenAndChecksIfTheTokenHasBeenAdded() throws FakeToken, TokenAlreadyUsed {
        tokenAdapter.createToken(token);
        Assert.assertTrue(tokenAdapter.checkToken(token));
    }

    private void CreateCustomer(boolean used) {
        customerAdapter.createCustomer(customer);
        token.setUsed(used);
        tokenAdapter.createToken(token);
    }
}

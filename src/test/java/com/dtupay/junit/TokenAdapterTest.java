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
    Customer customerNoToken;

    @Before
    public void Setup() {
        customerAdapter = new CustomerAdapter();
        tokenAdapter = new TokenAdapter();

        customer = customerAdapter.createCustomer("1", "Test");
        token = tokenAdapter.createToken(new Token(1, UUID.randomUUID(), customer.getId()));

        customerNoToken = customerAdapter.createCustomer("2", "Test");
    }

    @Test
    public void GetUnusedTokenByCustomerIdWithAUnusedToken() throws CustomerHasNoUnusedToken {
        Token actualToken = tokenAdapter.getUnusedTokenByCustomerId(customer.getId());
        Assert.assertEquals(token, actualToken);
    }

    @Test(expected = CustomerHasNoUnusedToken.class)
    public void GetUnusedTokenByCustomerIdWithNoUnusedToken() throws CustomerHasNoUnusedToken {
        tokenAdapter.getUnusedTokenByCustomerId(customerNoToken.getId());
    }

    @Test
    public void CreateATokenAndChecksIfTheTokenHasBeenAdded() throws FakeToken, TokenAlreadyUsed {
        tokenAdapter.createToken(token);
        Assert.assertTrue(tokenAdapter.checkToken(token));
    }
}

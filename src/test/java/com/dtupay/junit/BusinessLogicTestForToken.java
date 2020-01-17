package com.dtupay.junit;

import com.dtupay.BusinessLogic.BusinessLogicForCustomer;
import com.dtupay.BusinessLogic.BusinessLogicForToken;
import com.dtupay.BusinessLogic.IBusinessLogicForCustomer;
import com.dtupay.BusinessLogic.IBusinessLogicForToken;
import com.dtupay.app.Customer;
import com.dtupay.app.Token;
import com.dtupay.database.exceptions.CustomerHasNoUnusedToken;
import com.dtupay.database.exceptions.FakeToken;
import com.dtupay.database.exceptions.TokenAlreadyUsed;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class BusinessLogicTestForToken {

    IBusinessLogicForCustomer customerAdapter;
    IBusinessLogicForToken tokenAdapter;
    Token token;
    Customer customer;

    @Before
    public void Setup() {
        customerAdapter = new BusinessLogicForCustomer();
        tokenAdapter = new BusinessLogicForToken();
        customer = customerAdapter.CreateCustomer("9876", "BLCustomer");
        token = new Token(1, UUID.randomUUID(), customer.getId());
    }

    @Test
    public void GetUnusedTokenByCustomerIdWithAUnusedToken() throws CustomerHasNoUnusedToken {
        CreateToken(false);
        Token actualToken = tokenAdapter.GetUnusedTokenByCustomerId(customer.getId());
        Assert.assertEquals(token, actualToken);
    }

    @Test(expected = CustomerHasNoUnusedToken.class)
    public void GetUnusedTokenByCustomerIdWithNoUnusedToken() throws CustomerHasNoUnusedToken {
        CreateToken(true);
        tokenAdapter.GetUnusedTokenByCustomerId(customer.getId());
    }

    @Test
    public void CreateATokenAndChecksIfTheTokenHasBeenAdded() throws FakeToken, TokenAlreadyUsed {
        tokenAdapter.CreateToken(token);
        Assert.assertTrue(tokenAdapter.CheckToken(token));
    }

    private void CreateToken(boolean used) {
        token.setUsed(used);
        tokenAdapter.CreateToken(token);
    }
}

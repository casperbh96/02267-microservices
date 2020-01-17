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

    IBusinessLogicForCustomer customerLogic;
    IBusinessLogicForToken tokenLogic;
    Token token;
    Customer customer;
    Customer customerNoToken;

    @Before
    public void Setup() {
        customerLogic = new BusinessLogicForCustomer();
        tokenLogic = new BusinessLogicForToken();
        customer = customerLogic.CreateCustomer("9876", "BLCustomer");
        customerNoToken = customerLogic.CreateCustomer("211", "BLLCustomer2");
        token = new Token(1, customer.getId(), UUID.randomUUID(), false);
    }

    @Test
    public void GetUnusedTokenByCustomerIdWithAUnusedToken() throws CustomerHasNoUnusedToken {
        Token newToken = tokenLogic.CreateToken(token);
        Token actualToken = tokenLogic.GetUnusedTokenByCustomerId(customer.getId());
        Assert.assertEquals(newToken.getCustomerId(), actualToken.getCustomerId());
    }

    @Test(expected = CustomerHasNoUnusedToken.class)
    public void GetUnusedTokenByCustomerIdWithNoUnusedToken() throws CustomerHasNoUnusedToken {
        tokenLogic.GetUnusedTokenByCustomerId(customerNoToken.getId());
    }

    @Test
    public void CreateATokenAndChecksIfTheTokenHasBeenAdded() throws FakeToken, TokenAlreadyUsed {
        Token newToken = tokenLogic.CreateToken(token);
        Assert.assertTrue(tokenLogic.isTokenValid(newToken));
    }
}

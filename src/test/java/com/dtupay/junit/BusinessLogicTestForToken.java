package com.dtupay.junit;

import com.dtupay.BusinessLogic.BusinessLogicForCustomer;
import com.dtupay.BusinessLogic.BusinessLogicForToken;
import com.dtupay.BusinessLogic.IBusinessLogicForCustomer;
import com.dtupay.BusinessLogic.IBusinessLogicForToken;
import com.dtupay.app.Customer;
import com.dtupay.app.Token;
import com.dtupay.database.exceptions.CustomerHasNoUnusedToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class BusinessLogicTestForToken {

    IBusinessLogicForCustomer customerLogic;
    IBusinessLogicForToken tokenLogic;
    Customer customer;
    Customer customerNoToken;

    @Before
    public void Setup() {
        customerLogic = new BusinessLogicForCustomer();
        tokenLogic = new BusinessLogicForToken();
        customer = customerLogic.createCustomer("9876", "BLCustomer");
        customerNoToken = customerLogic.createCustomer("211", "BLLCustomer2");
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
}

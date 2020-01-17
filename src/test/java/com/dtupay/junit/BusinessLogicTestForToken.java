package com.dtupay.junit;

import com.dtupay.BusinessLogic.BusinessLogicForCustomer;
import com.dtupay.BusinessLogic.BusinessLogicForToken;
import com.dtupay.BusinessLogic.IBusinessLogicForCustomer;
import com.dtupay.BusinessLogic.IBusinessLogicForToken;
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

public class BusinessLogicTestForToken {

    IBusinessLogicForCustomer customerAdapter;
    IBusinessLogicForToken tokenAdapter;
    Token token;
    Customer customer;
    int customerId = 99;

    @Before
    public void Setup() {
        customerAdapter = new BusinessLogicForCustomer();
        tokenAdapter = new BusinessLogicForToken();
        token = new Token(1, UUID.randomUUID(), customerId);
        customer = new Customer(customerId, "9876", "BLCustomer");
    }

    @Test
    public void GetUnusedTokenByCustomerIdWithAUnusedToken() throws CustomerHasNoUnusedToken {
        CreateCustomer(false);
        Token actualToken = tokenAdapter.GetUnusedTokenByCustomerId(customerId);
        Assert.assertEquals(token, actualToken);
    }

    @Test(expected = CustomerHasNoUnusedToken.class)
    public void GetUnusedTokenByCustomerIdWithNoUnusedToken() throws CustomerHasNoUnusedToken {
        CreateCustomer(true);
        tokenAdapter.GetUnusedTokenByCustomerId(customerId);
    }

    @Test
    public void CreateATokenAndChecksIfTheTokenHasBeenAdded() throws FakeToken, TokenAlreadyUsed {
        tokenAdapter.CreateToken(token);
        Assert.assertTrue(tokenAdapter.CheckToken(token));
    }

    private void CreateCustomer(boolean used) {
        customerAdapter.CreateCustomer(customer);
        token.setUsed(used);
        tokenAdapter.CreateToken(token);
    }
}

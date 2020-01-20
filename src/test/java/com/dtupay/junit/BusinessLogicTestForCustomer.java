package com.dtupay.junit;

import com.dtupay.BusinessLogic.BusinessLogicForCustomer;
import com.dtupay.BusinessLogic.IBusinessLogicForCustomer;
import com.dtupay.app.Customer;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BusinessLogicTestForCustomer {
    IBusinessLogicForCustomer c;

    @Before
    public void Setup() {
        c = new BusinessLogicForCustomer();
    }

    @Test
    public void CreateCustomerTest() throws CustomerDoesNotExist {
        String cpr = "55";
        Customer newCustomer = c.createCustomer(cpr, "Test");
        Assert.assertEquals(cpr, newCustomer.getCpr());
    }

    @Test(expected = CustomerDoesNotExist.class)
    public void DeleteCustomerTest() throws CustomerDoesNotExist {
        Customer newCustomer = c.createCustomer("56", "Test");

        c.deleteCustomerByCustomerId(newCustomer.getId());
        c.getCustomerByCustomerId(newCustomer.getId());
    }

    @Test
    public void UpdateCustomerTest() throws CustomerDoesNotExist {
        String newName = "Test2";

        Customer newCustomer = c.createCustomer("55", "Test");

        newCustomer.setName(newName);
        newCustomer = c.updateCustomer(newCustomer.getId(), newCustomer.getCpr(), newCustomer.getName());

        Assert.assertEquals(newName, newCustomer.getName());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void ThrowsCustomerDoesNotExist() throws CustomerDoesNotExist {
        thrown.expect(CustomerDoesNotExist.class);
        throw new CustomerDoesNotExist("");
    }

}

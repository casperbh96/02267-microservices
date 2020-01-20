package com.customer.junit;

import com.customer.Manager.CustomerManager;
import com.customer.Manager.ICustomerManager;
import com.customer.app.Customer;
import com.customer.database.exceptions.CustomerDoesNotExist;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BusinessLogicTestForCustomer {
    ICustomerManager c;

    @Before
    public void Setup() {
        c = new CustomerManager();
    }

    @Test
    public void CreateCustomerTest() throws CustomerDoesNotExist {
        String cpr = "55";
        Customer newCustomer = c.CreateCustomer(cpr, "Test");
        Assert.assertEquals(cpr, newCustomer.getCpr());
    }

    @Test(expected = CustomerDoesNotExist.class)
    public void DeleteCustomerTest() throws CustomerDoesNotExist {
        Customer newCustomer = c.CreateCustomer("56", "Test");

        c.DeleteCustomerByCustomerId(newCustomer.getId());
        c.GetCustomerByCustomerId(newCustomer.getId());
    }

    @Test
    public void UpdateCustomerTest() throws CustomerDoesNotExist {
        String newName = "Test2";

        Customer newCustomer = c.CreateCustomer("55", "Test");

        newCustomer.setName(newName);
        newCustomer = c.UpdateCustomer(newCustomer.getId(), newCustomer.getCpr(), newCustomer.getName());

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

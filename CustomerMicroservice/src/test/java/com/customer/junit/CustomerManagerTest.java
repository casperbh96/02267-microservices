package com.customer.junit;

import com.customer.manager.CustomerManager;
import com.customer.manager.ICustomerManager;
import com.customer.app.Customer;
import com.customer.database.exceptions.CustomerDoesNotExist;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Ismael
 * Junit testing class for customer manager
 */

public class CustomerManagerTest {
    ICustomerManager c;

    /**
     * setup a new customer manager variable
     */

    @Before
    public void Setup() {
        c = new CustomerManager();
    }

    /**
     * test for create customer
     * @throws CustomerDoesNotExist
     */

    @Test
    public void CreateCustomerTest() throws CustomerDoesNotExist {
        String cpr = "55";
        Customer newCustomer = c.CreateCustomer(cpr, "Test");
        Assert.assertEquals(cpr, newCustomer.getCpr());
    }

    /**
     * test for deletion of customer
     * @throws CustomerDoesNotExist
     */

    @Test(expected = CustomerDoesNotExist.class)
    public void DeleteCustomerTest() throws CustomerDoesNotExist {
        Customer newCustomer = c.CreateCustomer("56", "Test");

        c.DeleteCustomerByCustomerId(newCustomer.getId());
        c.GetCustomerByCustomerId(newCustomer.getId());
    }

    /**
     * update customer test
     * @throws CustomerDoesNotExist
     */

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

    /**
     * exception testing
     * @throws CustomerDoesNotExist
     */

    @Test
    public void ThrowsCustomerDoesNotExist() throws CustomerDoesNotExist {
        thrown.expect(CustomerDoesNotExist.class);
        throw new CustomerDoesNotExist("");
    }

}

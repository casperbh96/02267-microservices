package com.customer.junit;

import com.customer.app.Customer;
import com.customer.database.CustomerAdapter;
import com.customer.database.ICustomerAdapter;
import com.customer.database.exceptions.CustomerDoesNotExist;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Junit testing class for customer adapter
 */
public class CustomerAdapterTest {
    ICustomerAdapter c;

    /**
     * setup a new customer adapter variable
     */
    @Before
    public void Setup() {
        c = new CustomerAdapter();
    }

    /**
     * test for create customer
     * @throws CustomerDoesNotExist
     */
    @Test
    public void CreateCustomerTest() throws CustomerDoesNotExist {
        String cpr = "123";
        Customer createdCustomer = c.createCustomer(cpr, "Test");

        Assert.assertEquals(cpr, createdCustomer.getCpr());
    }

    /**
     * test for deletion of customer
     * @throws CustomerDoesNotExist
     */
    @Test(expected = CustomerDoesNotExist.class)
    public void DeleteCustomerTest() throws CustomerDoesNotExist {
        Customer customer = c.createCustomer("16", "Test");
        c.deleteCustomerByCustomerId(customer.getId());
        c.getCustomerByCustomerId(customer.getId());
    }

    /**
     * update customer test
     * @throws CustomerDoesNotExist
     */
    @Test
    public void UpdateCustomerTest() throws CustomerDoesNotExist {
        String newName = "Test2";

        Customer newCustomer = c.createCustomer("974", "Test1");
        newCustomer.setName(newName);
        newCustomer = c.updateCustomer(newCustomer.getId(), newCustomer.getCpr(), newCustomer.getName());

        Assert.assertEquals(newName, newCustomer.getName());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * exception testing
     * @throws CustomerDoesNotExist
     */
    @Test
    public void throwsCustomerDoesNotExist() throws CustomerDoesNotExist {
        thrown.expect(CustomerDoesNotExist.class);
        throw new CustomerDoesNotExist("");
    }
}

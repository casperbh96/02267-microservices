package com.dtupay.junit;

import com.dtupay.app.Customer;
import com.dtupay.database.CustomerAdapter;
import com.dtupay.database.ICustomerAdapter;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CustomerAdapterTest {
    ICustomerAdapter c;

    @Before
    public void Setup(){
        c = new CustomerAdapter();
    }

    @Test
    public void CreateCustomerTest() throws CustomerDoesNotExist {
        Customer customer = new Customer("123", "Test");
        Customer createdCustomer = c.createCustomer(customer);

        Assert.assertEquals(customer.getCpr(), createdCustomer.getCpr());
    }

    @Test(expected = CustomerDoesNotExist.class)
    public void DeleteCustomerTest() throws CustomerDoesNotExist {
        Customer customer = c.createCustomer(new Customer("Test"));
        c.deleteCustomerByCustomerId(customer.getId());
        c.getCustomerByCustomerId(customer.getId());
    }

    @Test
    public void UpdateCustomerTest() throws CustomerDoesNotExist {
        String newName = "Test2";

        Customer newCustomer = c.createCustomer(new Customer("974", "Test1"));
        newCustomer.setName(newName);
        newCustomer = c.updateCustomer(newCustomer);

        Assert.assertEquals(newName, newCustomer.getName());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throwsCustomerDoesNotExist() throws CustomerDoesNotExist{
        thrown.expect(CustomerDoesNotExist.class);
        throw new CustomerDoesNotExist("");
    }
}

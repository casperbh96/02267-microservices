package com.dtupay.junit;

import com.dtupay.app.Customer;
import com.dtupay.database.CustomerAdapter;
import com.dtupay.database.ICustomerAdapter;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.helper.CustomerIdGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CustomerAdapterTest {
    ICustomerAdapter c;
    CustomerIdGenerator gen;

    @Before
    public void Setup(){
        c = new CustomerAdapter();
        gen = new CustomerIdGenerator();
    }

    @Test
    public void CreateCustomerTest() throws CustomerDoesNotExist {
        Customer customer = new Customer("Test");
        Customer createdCustomer = c.createCustomer(customer);

        Assert.assertEquals(customer.getId(), createdCustomer.getId());
    }

    @Test(expected = CustomerDoesNotExist.class)
    public void DeleteCustomerTest() throws CustomerDoesNotExist {
        Customer customer = c.createCustomer(new Customer("Test"));
        c.deleteCustomerByCustomerId(customer.getId());
        c.getCustomerByCustomerId(customer.getId());
    }

    @Test
    public void UpdateCustomerTest() throws CustomerDoesNotExist {
        String name = "Test1";
        String newName = "Test2";

        Customer customer = new Customer(name);
        Customer newCustomer = c.createCustomer(customer);
        customer.setName(newName);
        c.updateCustomer(customer);

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

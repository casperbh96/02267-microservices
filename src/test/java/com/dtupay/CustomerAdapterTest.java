package com.dtupay;

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
        Customer customer = new Customer("55", "Test");
        c.createCustomer(customer);
        Assert.assertEquals(customer, c.getCustomerByCustomerId("55"));
    }

    @Test(expected = CustomerDoesNotExist.class)
    public void DeleteCustomerTest() throws CustomerDoesNotExist {
        String id = "56";
        c.createCustomer(new Customer(id, "Test"));
        c.deleteCustomerByCustomerId(id);
        c.getCustomerByCustomerId(id);
    }

    @Test
    public void UpdateCustomerTest() throws CustomerDoesNotExist {
        Customer customer = new Customer("57", "Test1");
        c.createCustomer(customer);
        customer.setName("Test2");
        c.updateCustomer(customer);
        Assert.assertEquals(customer, c.getCustomerByCustomerId("57"));
    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throwsCustomerDoesNotExist() throws CustomerDoesNotExist{
        thrown.expect(CustomerDoesNotExist.class);
        throw new CustomerDoesNotExist("");
    }
}

package com.dtupay.BusinessLogicJunitTest;

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
    public void Setup(){
        c = new BusinessLogicForCustomer();
    }

    @Test
    public void CreateCustomerTest() throws CustomerDoesNotExist {
        Customer customer = new Customer("55", "Test");
        c.CreateCustomer(customer);
        Assert.assertEquals(customer, c.GetCustomerByCustomerId("55"));
    }

    @Test(expected = CustomerDoesNotExist.class)
    public void DeleteCustomerTest() throws CustomerDoesNotExist {
        String id = "56";
        c.CreateCustomer(new Customer(id, "Test"));
        c.DeleteCustomerByCustomerId(id);
        c.GetCustomerByCustomerId(id);
    }

    @Test
    public void UpdateCustomerTest() throws CustomerDoesNotExist {
        Customer customer = new Customer("57", "Test1");
        c.CreateCustomer(customer);
        customer.setName("Test2");
        c.UpdateCustomer(customer);
        Assert.assertEquals(customer, c.GetCustomerByCustomerId("57"));
    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void ThrowsCustomerDoesNotExist() throws CustomerDoesNotExist{
        thrown.expect(CustomerDoesNotExist.class);
        throw new CustomerDoesNotExist("");
    }

}

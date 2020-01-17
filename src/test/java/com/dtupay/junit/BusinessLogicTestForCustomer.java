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
    public void Setup(){
        c = new BusinessLogicForCustomer();
    }

    @Test
    public void CreateCustomerTest() throws CustomerDoesNotExist {
        Customer customer = new Customer("55", "Test");
        Customer newCustomer = c.CreateCustomer(customer);
        Assert.assertEquals(customer.getCpr(), newCustomer.getCpr());
    }

    @Test(expected = CustomerDoesNotExist.class)
    public void DeleteCustomerTest() throws CustomerDoesNotExist {
        Customer customer = new Customer("56", "Test");
        Customer newCustomer = c.CreateCustomer(customer);

        c.DeleteCustomerByCustomerId(newCustomer.getId());
        c.GetCustomerByCustomerId(newCustomer.getId());
    }

    @Test
    public void UpdateCustomerTest() throws CustomerDoesNotExist {
        String newName = "Test2";

        Customer customer = new Customer("55", "Test");
        Customer newCustomer = c.CreateCustomer(customer);

        newCustomer.setName(newName);
        newCustomer = c.UpdateCustomer(newCustomer);

        Assert.assertEquals(newName, newCustomer.getName());
    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void ThrowsCustomerDoesNotExist() throws CustomerDoesNotExist{
        thrown.expect(CustomerDoesNotExist.class);
        throw new CustomerDoesNotExist("");
    }

}

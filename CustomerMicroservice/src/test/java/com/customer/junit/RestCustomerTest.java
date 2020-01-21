package com.customer.junit;

import com.customer.manager.CustomerManager;
import com.customer.manager.ICustomerManager;
import com.customer.app.Customer;
import com.customer.database.exceptions.CustomerDoesNotExist;
import com.customer.service.CustomerResource;
import com.customer.service.exceptions.CustomerResourceException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * junit testing for customer rest services
  */
public class RestCustomerTest {

    ICustomerManager c = new CustomerManager();
    CustomerResource cr = new CustomerResource();
    Customer customer;

    /**
     * set up the customer
     */
    @Before
    public void Setup() {
        customer = c.CreateCustomer("12345678", "Ismaa");
    }

    /**
     * delete the customer by customer id
     * @throws CustomerDoesNotExist
     */
    @After
    public void cleanUp() throws CustomerDoesNotExist {
        c.DeleteCustomerByCustomerId(customer.getId());
    }

    /**
     * test for getting all the customers on call
     */
    @Test
    public void GetAllCustomersWithNoException() {
        cr.getCustomers();
    }

    /**
     * test for getting a customer on call
     */
    @Test
    public void GetCustomerWithNoException() {
        cr.getCustomer(customer.getId());
    }

    /**
     * checks if it asserts for bad request when asking for customers with wrong id
     * @throws CustomerResourceException
     */
    @Test
    public void ChecksIfThereIsThrownAnExceptionWhenGivingWrongIDToGetCustomers() throws CustomerResourceException {
        Assert.assertEquals(400, cr.getCustomer(1111111111).getStatus());
    }

    /**
     * checks for the post request for customer
     */
    @Test
    public void PostCustomerWithNoException() {
        String customerName = "Manolo";
        String customerCPR = "999999";
        final String POST_PARAMS = "{\n" + "\"name\": \""+customerName+"\",\r\n" +
                "    \"cpr\": \""+customerCPR+"\"" + "\n}";
        cr.postCustomer(POST_PARAMS);
    }

    /**
     * checks for the put request for customer
     */
    @Test
    public void PutCustomerWithNoException() {
        String customerName = "Updated Manolo";
        String customerCPR = "update-999999";
        final String PUT_PARAMS = "{\n" + "\"name\": \""+customerName+"\",\r\n" +
                "    \"id\": "+customer.getId()+",\r\n" +
                "    \"cpr\": \""+customerCPR+"\"" + "\n}";
        cr.putCustomer(PUT_PARAMS);
    }

    /**
     * checks for deletion of customer using rest service
     */
    @Test
    public void DeleteCustomerWithNoException() {
        cr.deleteCustomer(customer.getId());
    }

}

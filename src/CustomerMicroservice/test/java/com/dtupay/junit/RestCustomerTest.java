package com.dtupay.junit;

import com.dtupay.BusinessLogic.BusinessLogicForCustomer;
import com.dtupay.BusinessLogic.IBusinessLogicForCustomer;
import com.dtupay.app.Customer;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.service.CustomerResource;
import com.dtupay.service.exceptions.CustomerResourceException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RestCustomerTest {

    IBusinessLogicForCustomer c = new BusinessLogicForCustomer();
    CustomerResource cr = new CustomerResource();
    Customer customer;

    @Before
    public void Setup() {
        customer = c.CreateCustomer("12345678", "Ismaa");
    }

    @After
    public void cleanUp() throws CustomerDoesNotExist {
        c.DeleteCustomerByCustomerId(customer.getId());
    }

    @Test
    public void GetAllCustomersWithNoException() {
        cr.getCustomers();
    }

    @Test
    public void GetCustomerWithNoException() {
        cr.getCustomer(customer.getId());
    }

    @Test
    public void ChecksIfThereIsThrownAnExceptionWhenGivingWrongIDToGetCustomers() throws CustomerResourceException {
        Assert.assertEquals(400, cr.getCustomer(1111111111).getStatus());
    }

    @Test
    public void PostCustomerWithNoException() {
        String customerName = "Manolo";
        String customerCPR = "999999";
        final String POST_PARAMS = "{\n" + "\"name\": \""+customerName+"\",\r\n" +
                "    \"cpr\": \""+customerCPR+"\"" + "\n}";
        cr.postCustomer(POST_PARAMS);
    }

    @Test
    public void PutCustomerWithNoException() {
        String customerName = "Updated Manolo";
        String customerCPR = "update-999999";
        final String PUT_PARAMS = "{\n" + "\"name\": \""+customerName+"\",\r\n" +
                "    \"id\": "+customer.getId()+",\r\n" +
                "    \"cpr\": \""+customerCPR+"\"" + "\n}";
        cr.putCustomer(PUT_PARAMS);
    }

    @Test
    public void DeleteCustomerWithNoException() {
        cr.deleteCustomer(customer.getId());
    }

}

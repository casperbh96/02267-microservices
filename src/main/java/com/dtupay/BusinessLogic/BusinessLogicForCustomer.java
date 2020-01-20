package com.dtupay.BusinessLogic;

import com.dtupay.app.Customer;
import com.dtupay.database.CustomerAdapter;
import com.dtupay.database.ICustomerAdapter;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.exceptions.NoCustomers;

import java.util.List;

public class BusinessLogicForCustomer implements IBusinessLogicForCustomer {

    ICustomerAdapter customerAdapter = new CustomerAdapter();

    public Customer createCustomer(String cpr, String name) {
        return customerAdapter.createCustomer(cpr, name);
    }

    public Customer updateCustomer(int id, String cpr, String name) throws CustomerDoesNotExist {
        return customerAdapter.updateCustomer(id, cpr, name);
    }

    public void deleteCustomerByCustomerId(int id) throws CustomerDoesNotExist {
        customerAdapter.deleteCustomerByCustomerId(id);
    }

    public Customer getCustomerByCustomerId(int id) throws CustomerDoesNotExist {
        return customerAdapter.getCustomerByCustomerId(id);
    }

    public List<Customer> getAllCustomers() throws NoCustomers {
        return customerAdapter.getAllCustomers();
    }

}

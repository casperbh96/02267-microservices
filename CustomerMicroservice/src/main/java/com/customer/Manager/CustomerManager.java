package com.customer.Manager;

import com.customer.app.Customer;
import com.customer.database.CustomerAdapter;
import com.customer.database.ICustomerAdapter;
import com.customer.database.exceptions.CustomerDoesNotExist;
import com.customer.database.exceptions.NoCustomers;

import java.util.List;

public class CustomerManager implements ICustomerManager {

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

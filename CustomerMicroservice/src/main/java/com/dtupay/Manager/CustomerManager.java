package com.dtupay.Manager;

import com.dtupay.app.Customer;
import com.dtupay.database.CustomerAdapter;
import com.dtupay.database.ICustomerAdapter;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.exceptions.NoCustomers;

import java.util.List;

public class CustomerManager implements ICustomerManager {

    ICustomerAdapter customerAdapter = new CustomerAdapter();

    public Customer CreateCustomer(String cpr, String name) {
        return customerAdapter.createCustomer(cpr, name);
    }

    public Customer UpdateCustomer(int id, String cpr, String name) throws CustomerDoesNotExist {
        return customerAdapter.updateCustomer(id, cpr, name);
    }

    public void DeleteCustomerByCustomerId(int id) throws CustomerDoesNotExist {
        customerAdapter.deleteCustomerByCustomerId(id);
    }

    public Customer GetCustomerByCustomerId(int id) throws CustomerDoesNotExist {
        return customerAdapter.getCustomerByCustomerId(id);
    }

    public List<Customer> GetAllCustomers() throws NoCustomers {
        return customerAdapter.getAllCustomers();
    }

}

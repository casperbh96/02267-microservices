package com.customer.manager;

import com.customer.app.Customer;
import com.customer.database.CustomerAdapter;
import com.customer.database.ICustomerAdapter;
import com.customer.database.exceptions.CustomerDoesNotExist;
import com.customer.database.exceptions.NoCustomers;

import java.util.List;

/**
 * @author Anshul
 * Customer manager links the database to DtuPayApp. Acts as an intermediate between both
 */
public class CustomerManager implements ICustomerManager {

    ICustomerAdapter customerAdapter = new CustomerAdapter();

    /**
     * generates new customer
     * @param cpr number
     * @param name customer name
     * @return customer
     */
    public Customer CreateCustomer(String cpr, String name) {
        return customerAdapter.createCustomer(cpr, name);
    }

    /**
     * updates the customer information
     * @param id customer id
     * @param cpr number
     * @param name customer name
     * @return customer
     * @throws CustomerDoesNotExist
     */
    public Customer UpdateCustomer(int id, String cpr, String name) throws CustomerDoesNotExist {
        return customerAdapter.updateCustomer(id, cpr, name);
    }

    /**
     * remove customers from the customers list
     * @param id customer id
     * @throws CustomerDoesNotExist
     */
    public void DeleteCustomerByCustomerId(int id) throws CustomerDoesNotExist {
        customerAdapter.deleteCustomerByCustomerId(id);
    }

    /**
     * gives customer using customer id
     * @param id customer id
     * @return customer
     * @throws CustomerDoesNotExist
     */
    public Customer GetCustomerByCustomerId(int id) throws CustomerDoesNotExist {
        return customerAdapter.getCustomerByCustomerId(id);
    }

    /**
     * gives the list the list of all customers
     * @return list of customers
     * @throws NoCustomers
     */
    public List<Customer> GetAllCustomers() throws NoCustomers {
        return customerAdapter.getAllCustomers();
    }

}

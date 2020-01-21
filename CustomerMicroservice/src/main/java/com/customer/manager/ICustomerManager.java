package com.customer.manager;

import com.customer.app.Customer;
import com.customer.database.exceptions.CustomerDoesNotExist;
import com.customer.database.exceptions.NoCustomers;

import java.util.List;

/**
 * interface to handle customer manager class
 */
public interface ICustomerManager {
    List<Customer> GetAllCustomers() throws NoCustomers;

    Customer GetCustomerByCustomerId(int id) throws CustomerDoesNotExist;

    Customer CreateCustomer(String cpr, String name);

    Customer UpdateCustomer(int id, String cpr, String name) throws CustomerDoesNotExist;

    void DeleteCustomerByCustomerId(int id) throws CustomerDoesNotExist;
}

package com.customer.Manager;

import com.customer.app.Customer;
import com.customer.database.exceptions.CustomerDoesNotExist;
import com.customer.database.exceptions.NoCustomers;

import java.util.List;

public interface ICustomerManager {
    List<Customer> getAllCustomers() throws NoCustomers;

    Customer getCustomerByCustomerId(int id) throws CustomerDoesNotExist;

    Customer createCustomer(String cpr, String name);

    Customer updateCustomer(int id, String cpr, String name) throws CustomerDoesNotExist;

    void deleteCustomerByCustomerId(int id) throws CustomerDoesNotExist;
}

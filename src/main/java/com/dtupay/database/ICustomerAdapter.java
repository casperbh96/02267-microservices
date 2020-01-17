package com.dtupay.database;

import com.dtupay.app.Customer;
import com.dtupay.database.exceptions.*;

import java.util.List;

public interface ICustomerAdapter {
    List<Customer> getAllCustomers() throws NoCustomers;
    Customer getCustomerByCustomerId(String id) throws CustomerDoesNotExist;
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer) throws CustomerDoesNotExist;
    void deleteCustomerByCustomerId(String id) throws CustomerDoesNotExist;
}

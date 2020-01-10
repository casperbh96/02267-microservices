package com.calculator.database;

import com.calculator.Customer;
import com.calculator.database.exceptions.*;

public interface ICustomerAdapter {
    Customer getCustomerByCustomerId(int id) throws CustomerDoesNotExist;
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer) throws CustomerDoesNotExist;
    void deleteCustomerByCustomerId(int id) throws CustomerDoesNotExist;
}

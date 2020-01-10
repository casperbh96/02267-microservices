package com.calculator.database;

import com.calculator.Customer;
import com.calculator.database.exceptions.*;

public interface ICustomerAccess {
    Customer getCustomerByCustomerId(int id) throws CustomerDoesNotExist;
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    void deleteCustomerByCustomerId(int id);
}

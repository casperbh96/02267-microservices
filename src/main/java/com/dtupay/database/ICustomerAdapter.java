package com.dtupay.database;

import com.dtupay.app.Customer;
import com.dtupay.database.exceptions.*;

public interface ICustomerAdapter {
    Customer getCustomerByCustomerId(String id) throws CustomerDoesNotExist;
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer) throws CustomerDoesNotExist;
    void deleteCustomerByCustomerId(String id) throws CustomerDoesNotExist;
}

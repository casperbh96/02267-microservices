package com.dtupay.database;

import com.dtupay.app.Customer;
import com.dtupay.database.exceptions.*;

import java.util.List;

public interface ICustomerAdapter {
    List<Customer> getAllCustomers() throws NoCustomers;
    Customer getCustomerByCustomerId(int id) throws CustomerDoesNotExist;
    Customer createCustomer(String cpr, String name) ;
    Customer updateCustomer(int id, String cpr, String name) throws CustomerDoesNotExist;
    void deleteCustomerByCustomerId(int id) throws CustomerDoesNotExist;
}

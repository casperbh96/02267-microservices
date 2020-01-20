package com.dtupay.Manager;

import com.dtupay.app.Customer;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.exceptions.NoCustomers;

import java.util.List;

public interface ICustomerManager {
    List<Customer> GetAllCustomers() throws NoCustomers;

    Customer GetCustomerByCustomerId(int id) throws CustomerDoesNotExist;

    Customer CreateCustomer(String cpr, String name);

    Customer UpdateCustomer(int id, String cpr, String name) throws CustomerDoesNotExist;

    void DeleteCustomerByCustomerId(int id) throws CustomerDoesNotExist;
}

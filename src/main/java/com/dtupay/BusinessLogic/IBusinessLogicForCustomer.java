package com.dtupay.BusinessLogic;

import com.dtupay.app.Customer;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.exceptions.NoCustomers;

import java.util.List;

public interface IBusinessLogicForCustomer {
    List<Customer> getAllCustomers() throws NoCustomers;

    Customer getCustomerByCustomerId(int id) throws CustomerDoesNotExist;

    Customer createCustomer(String cpr, String name);

    Customer updateCustomer(int id, String cpr, String name) throws CustomerDoesNotExist;

    void deleteCustomerByCustomerId(int id) throws CustomerDoesNotExist;
}

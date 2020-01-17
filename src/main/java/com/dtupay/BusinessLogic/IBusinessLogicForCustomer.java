package com.dtupay.BusinessLogic;

import com.dtupay.app.Customer;
import com.dtupay.database.exceptions.CustomerDoesNotExist;

public interface IBusinessLogicForCustomer {
    Customer GetCustomerByCustomerId(String id) throws CustomerDoesNotExist;
    Customer CreateCustomer(Customer customer);
    Customer UpdateCustomer(Customer customer) throws CustomerDoesNotExist;
    void DeleteCustomerByCustomerId(String id) throws CustomerDoesNotExist;
}

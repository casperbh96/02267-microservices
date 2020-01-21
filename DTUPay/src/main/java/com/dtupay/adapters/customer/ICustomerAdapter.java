package com.dtupay.adapters.customer;

import com.dtupay.adapters.customer.exceptions.CustomerException;
import com.dtupay.adapters.customer.model.Customer;

/**
 * @author Dumitru
 * interface to handle customer adapter class
 */
public interface ICustomerAdapter {
    Customer createCustomer(String cpr, String name) throws CustomerException;

    Customer updateCustomer(int customerId, String cpr, String name) throws CustomerException;

    Customer getCustomerById(int customerId) throws CustomerException;
}

package com.calculator.database;

import com.calculator.database.exceptions.*;

public interface ICustomerAccess {
    // TODO: return Customer class instead of void
    void getCustomerByCustomerId(int id) throws CustomerDoesNotExist;
    void createCustomer(String name, String password);
    void updateCustomerByCustomerId(int id, String name, String password);
    void deleteCustomerByCustomerId(int id);
}

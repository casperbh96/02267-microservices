package com.calculator.database;

import com.calculator.database.exceptions.*;

public interface ICustomerAccess {
    // TODO: return Customer class instead of void
    void getCustomerByCustomerId(int id) throws CustomerDoesNotExist;
    void createCustomer(); // TODO: use customer object instead
    void updateCustomerByCustomerId(); // TODO: use customer object instead
    void deleteCustomerByCustomerId(int id);
}

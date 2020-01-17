package com.dtupay.BusinessLogic;

import com.dtupay.app.Customer;
import com.dtupay.database.CustomerAdapter;
import com.dtupay.database.ICustomerAdapter;
import com.dtupay.database.exceptions.CustomerDoesNotExist;

import java.util.List;

public class BusinessLogicForCustomer implements IBusinessLogicForCustomer{

    ICustomerAdapter customerAdapter = new CustomerAdapter();

    public Customer CreateCustomer(Customer customer){
        return customerAdapter.createCustomer(customer);
    }

    public Customer UpdateCustomer(Customer customer) throws CustomerDoesNotExist {
        return customerAdapter.updateCustomer(customer);
    }

    public void  DeleteCustomerByCustomerId(int id) throws CustomerDoesNotExist {
        customerAdapter.deleteCustomerByCustomerId(id);
    }

    public Customer GetCustomerByCustomerId(int id) throws CustomerDoesNotExist {
        return customerAdapter.getCustomerByCustomerId(id);
    }

}

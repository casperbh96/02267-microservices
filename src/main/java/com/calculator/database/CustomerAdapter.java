package com.calculator.database;

import com.calculator.Customer;
import com.calculator.database.exceptions.CustomerDoesNotExist;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter implements ICustomerAdapter {
    public List<Customer> customers;

    public CustomerAdapter() {
        customers = new ArrayList<>();
        customers.add(new Customer(1, "Casper"));
        customers.add(new Customer(2, "Nela"));
        customers.add(new Customer(3, "Ansh"));
        customers.add(new Customer(4, "Danial"));
        customers.add(new Customer(5, "Dmitry"));
        customers.add(new Customer(6, "Isma"));
        customers.add(new Customer(7, "Hilda"));
    }

    @Override
    public Customer getCustomerByCustomerId(int id) throws CustomerDoesNotExist {
        for (Customer c : customers) {
            if (c.getId() == id) return c;
        }

        throw new CustomerDoesNotExist(MessageFormat.format(
                "Customer id {0} was not found in customer list.", id));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        customers.add(customer);
        return customer;
    }

    @Override
    public Customer updateCustomer(Customer customer) throws CustomerDoesNotExist {
        deleteCustomerByCustomerId(customer.getId());
        return createCustomer(customer);
    }

    @Override
    public void deleteCustomerByCustomerId(int id) throws CustomerDoesNotExist {
        Customer customer = getCustomerByCustomerId(id);
        customers.remove(customer);
    }
}

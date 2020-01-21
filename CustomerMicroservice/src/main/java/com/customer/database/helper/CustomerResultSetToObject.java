package com.customer.database.helper;

import com.customer.app.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Casper
 * defined class which contains functions to get a new customer with information from result sets.
 */

public class CustomerResultSetToObject {

    /**
     * gives information about customer from result set
     * @param set
     * @return new customer
     * @throws SQLException
     */
    public Customer resultSetToCustomer(ResultSet set) throws SQLException {
        set.beforeFirst();
        set.next();

        int id = set.getInt(1);
        String cpr = set.getString(2);
        String name = set.getString(3);

        return new Customer(id, cpr, name);
    }

    /**
     * gives list which consists information in result set
     * @param set
     * @return list of customers
     * @throws SQLException
     */
    public List<Customer> resultSetToListOfCustomers(ResultSet set) throws SQLException {
        List<Customer> customerList = new ArrayList<>();

        while(set.next()) {
            int id = set.getInt(1);
            String cpr = set.getString(2);
            String name = set.getString(3);

            Customer newCustomer = new Customer(id, cpr, name);
            customerList.add(newCustomer);
        }

        return customerList;
    }
}

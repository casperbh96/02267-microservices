package com.dtupay.database.helper;

import com.dtupay.app.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerResultSetToObject {
    public Customer resultSetToCustomer(ResultSet set) throws SQLException {
        set.beforeFirst();
        set.next();

        int id = set.getInt(1);
        String cpr = set.getString(2);
        String name = set.getString(3);

        return new Customer(id, cpr, name);
    }

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

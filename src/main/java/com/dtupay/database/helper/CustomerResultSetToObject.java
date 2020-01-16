package com.dtupay.database.helper;

import com.dtupay.app.Customer;
import com.dtupay.app.IDtuPayApp;
import com.dtupay.app.Token;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerResultSetToObject {
    public Customer resultSetToCustomer(ResultSet set) throws SQLException {
        set.beforeFirst();
        set.next();
        int id = set.getInt(1);
        String name = set.getString(2);

        return new Customer(id, name);
    }
}

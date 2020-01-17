package com.dtupay.database;

import com.dtupay.app.Customer;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.helper.CustomerIdGenerator;
import com.dtupay.database.helper.CustomerResultSetToObject;

import static com.dtupay.database.Connector.*;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter implements ICustomerAdapter {
    public List<Customer> customers;
    CustomerResultSetToObject converter = new CustomerResultSetToObject();
    CustomerIdGenerator gen = new CustomerIdGenerator();

    public CustomerAdapter() {
        customers = new ArrayList<>();
        /*
        customers.add(new Customer(1, "Casper"));
        customers.add(new Customer(2, "Nela"));
        customers.add(new Customer(3, "Ansh"));
        customers.add(new Customer(4, "Danial"));
        customers.add(new Customer(5, "Dmitry"));
        customers.add(new Customer(6, "Isma"));
        customers.add(new Customer(7, "Hilda"));
        */

    }

    @Override
    public Customer getCustomerByCustomerId(int id) throws CustomerDoesNotExist {
        Customer customer = null;
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM customer WHERE id = ?");
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();

            if (!rs.next()) throw new CustomerDoesNotExist(MessageFormat.format(
                            "Customer id {0} was not found in customer list.", id));

            customer = converter.resultSetToCustomer(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return customer;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer returnCustomer = null;
        int autoGenId = 0;
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "INSERT INTO customer (cpr, name) VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            query.setString(1, customer.getCpr());
            query.setString(2, customer.getName());

            query.executeUpdate();

            autoGenId = gen.getIdFromDbReturn(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            returnCustomer = getCustomerByCustomerId(autoGenId);
        } catch (CustomerDoesNotExist ex) {
            ex.printStackTrace();
        }

        return returnCustomer;
    }

    @Override
    public Customer updateCustomer(Customer customer) throws CustomerDoesNotExist {
        Customer returnCustomer = null;
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "UPDATE customer SET cpr = ?, name = ? WHERE id = ?");

            query.setString(1, customer.getCpr());
            query.setString(2, customer.getName());
            query.setInt(3, customer.getId());

            query.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            returnCustomer = getCustomerByCustomerId(customer.getId());
        } catch (CustomerDoesNotExist ex) {
            ex.printStackTrace();
        }

        return returnCustomer;
    }

    @Override
    public void deleteCustomerByCustomerId(int id) throws CustomerDoesNotExist {
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "DELETE FROM customer WHERE id = ?;");

            query.setInt(1, id);
            query.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

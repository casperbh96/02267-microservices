package com.dtupay.database;

import com.dtupay.app.Customer;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.helper.CustomerResultSetToObject;

import static com.dtupay.database.Connector.*;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter implements ICustomerAdapter {
    public List<Customer> customers;
    CustomerResultSetToObject converter = new CustomerResultSetToObject();

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
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "INSERT INTO customer (name) VALUES (?)");

            query.setString(1, customer.getName());

            query.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return customer;
    }

    @Override
    public Customer updateCustomer(Customer customer) throws CustomerDoesNotExist {
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "UPDATE customer SET name = ? WHERE id = ?");

            query.setInt(2, customer.getId());
            query.setString(1, customer.getName());

            query.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return customer;
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

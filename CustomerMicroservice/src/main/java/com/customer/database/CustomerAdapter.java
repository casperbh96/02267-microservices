package com.customer.database;

import com.customer.app.Customer;
import com.customer.database.exceptions.CustomerDoesNotExist;
import com.customer.database.helper.CustomerResultSetToObject;
import com.customer.database.exceptions.NoCustomers;

import java.sql.Connection;
import java.sql.SQLException;

import static com.customer.database.Connector.*;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dumitru
 * class to handle all customer related applications in the database
 */
public class CustomerAdapter implements ICustomerAdapter {
    CustomerResultSetToObject converter = new CustomerResultSetToObject();

    /**
     * shows all the customers in the customer list
     * @return list of customers
     * @throws NoCustomers
     */
    @Override
    public List<Customer> getAllCustomers() throws NoCustomers {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM customer");
            ResultSet rs = query.executeQuery();

            customers = converter.resultSetToListOfCustomers(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return customers;
    }

    /**
     * gets customer using customer id
     * @param id
     * @return customer
     * @throws CustomerDoesNotExist
     */
    @Override
    public Customer getCustomerByCustomerId(int id) throws CustomerDoesNotExist {
        Customer customer = null;
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM customer WHERE id = ?");
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();

            if (!rs.next()) throw new CustomerDoesNotExist(MessageFormat.format(
                            "Customer id {0} was not found in customer table.", id));

            customer = converter.resultSetToCustomer(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return customer;
    }

    /**
     * to create new customer
     * @param cpr number
     * @param name customer name
     * @return customer
     */
    @Override
    public Customer createCustomer(String cpr, String name) {
        Customer returnCustomer = null;
        int autoGenId = 0;
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "INSERT INTO customer (cpr, name) VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            query.setString(1, cpr);
            query.setString(2, name);

            query.executeUpdate();

            autoGenId = getIdFromDbReturn(query);
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

    /**
     * This class updates the customer with new data values
     * @param id customer id
     * @param cpr number
     * @param name customer name
     * @return customer
     * @throws CustomerDoesNotExist
     */
    @Override
    public Customer updateCustomer(int id, String cpr, String name) throws CustomerDoesNotExist {
        Customer returnCustomer = null;
        try (Connection connection = createConnection()) {
            PreparedStatement query = connection.prepareStatement(
                    "UPDATE customer SET cpr = ?, name = ? WHERE id = ?");

            query.setString(1, cpr);
            query.setString(2, name);
            query.setInt(3, id);

            query.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            returnCustomer = getCustomerByCustomerId(id);
        } catch (CustomerDoesNotExist ex) {
            ex.printStackTrace();
        }

        return returnCustomer;
    }

    /**
     * deletes customer using customer id
     * @param id
     * @throws CustomerDoesNotExist
     */
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

    /**
     * get customer id from database result set
     * @param stmt prepared statement
     * @return customer id
     * @throws SQLException
     */
    public int getIdFromDbReturn(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();

        return rs.getInt(1);
    }
}

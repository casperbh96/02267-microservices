package com.dtupay.database;

import com.dtupay.app.Customer;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.helper.CustomerResultSetToObject;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.dtupay.database.exceptions.NoCustomers;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static com.dtupay.database.Connector.*;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter implements ICustomerAdapter {
    CustomerResultSetToObject converter = new CustomerResultSetToObject();
    
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

    public int getIdFromDbReturn(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();

        return rs.getInt(1);
    }
}

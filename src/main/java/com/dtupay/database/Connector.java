package com.dtupay.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import cucumber.deps.difflib.myers.MyersDiff;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.text.MessageFormat;

public class Connector {

    public static MysqlDataSource dataSource;

    public static Connection createConnection() throws SQLException {
        dataSource = new MysqlDataSource();

        try {
            dataSource.setUser("root");
            dataSource.setPassword("1234");
            dataSource.setServerName("64.225.70.110");
            dataSource.setPort(5000);
            dataSource.setDatabaseName("DTUPay");
            dataSource.setCreateDatabaseIfNotExist(true);

            createTablesIfNotExists(dataSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getConnection(dataSource);
    }

    public static Connection getConnection(MysqlDataSource db) throws SQLException {
        return db.getConnection();
    }

    private static void createTablesIfNotExists(MysqlDataSource db) throws SQLException {
        try (Connection connection = getConnection(db)) {
            String sql = "CREATE TABLE IF NOT EXISTS customer (" +
                    "id INTEGER AUTO_INCREMENT, " +
                    "cpr VARCHAR(255), name VARCHAR(255), " +
                    "PRIMARY KEY (id))";
            Statement query = connection.createStatement();
            int success = query.executeUpdate(sql);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

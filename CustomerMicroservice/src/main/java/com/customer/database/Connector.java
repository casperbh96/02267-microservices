package com.customer.database;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;

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
            String customer = "CREATE TABLE IF NOT EXISTS customer (" +
                    "id INTEGER AUTO_INCREMENT, " +
                    "cpr VARCHAR(255), " +
                    "name VARCHAR(255), " +
                    "PRIMARY KEY (id))";
            String merchant = "CREATE TABLE IF NOT EXISTS merchant (" +
                    "id INTEGER AUTO_INCREMENT, " +
                    "cvr VARCHAR(255), " +
                    "name VARCHAR(255), " +
                    "PRIMARY KEY (id))";
            String token = "CREATE TABLE IF NOT EXISTS token (" +
                    "id INTEGER AUTO_INCREMENT, " +
                    "customerId INTEGER NOT NULL, " +
                    "uuid VARCHAR(255), " +
                    "used BOOLEAN, " +
                    "PRIMARY KEY (id))";
            String transaction = "CREATE TABLE IF NOT EXISTS transaction (" +
                    "id INTEGER AUTO_INCREMENT, " +
                    "timestamp TIMESTAMP," +
                    "fromId INTEGER NOT NULL, " +
                    "toId INTEGER NOT NULL, " +
                    "amount DECIMAL(5,2), " +
                    "tokenId VARCHAR(255), " +
                    "isRefund BOOLEAN, " +
                    "PRIMARY KEY (id))";
            Statement query = connection.createStatement();
            int customerSuccess = query.executeUpdate(customer);
            int merchantSuccess = query.executeUpdate(merchant);
            int tokenSuccess = query.executeUpdate(token);
            int transactionSuccess = query.executeUpdate(transaction);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

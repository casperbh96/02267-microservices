package com.merchant.database;

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
            dataSource.setDatabaseName("MerchantDb");
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
            String merchant = "CREATE TABLE IF NOT EXISTS merchant (" +
                    "id INTEGER AUTO_INCREMENT, " +
                    "cvr VARCHAR(255), " +
                    "name VARCHAR(255), " +
                    "PRIMARY KEY (id))";
            Statement query = connection.createStatement();
            int merchantSuccess = query.executeUpdate(merchant);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

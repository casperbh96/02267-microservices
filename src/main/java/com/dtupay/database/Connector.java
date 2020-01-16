package com.dtupay.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import cucumber.deps.difflib.myers.MyersDiff;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;

public class Connector {

    public MysqlDataSource dataSource;

    public MysqlDataSource createConnection() throws SQLException {
        dataSource = new MysqlDataSource();

        try {
            dataSource.setUser("root");
            dataSource.setPassword("1234");
            dataSource.setServerName("64.225.70.110");
            dataSource.setPort(5000);
            dataSource.setDatabaseName("DTUPay");
            dataSource.setCreateDatabaseIfNotExist(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataSource;
    }
}

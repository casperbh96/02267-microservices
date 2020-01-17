package com.dtupay.database.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class CustomerIdGenerator {
    public int generateCprNumber(){
        Random r = new Random();
        int low     = 1;
        int high    = 999999999;
        return r.nextInt(high-low) + low;
    }

    public int getIdFromDbReturn(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();

        return rs.getInt(1);
    }
}

package com.dtupay.database.helper;

import com.dtupay.app.Merchant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MerchantResultSetToObject {
    public Merchant resultSetToMerchant(ResultSet set) throws SQLException {
        set.beforeFirst();
        set.next();

        int id = set.getInt(1);
        String cvr = set.getString(2);
        String name = set.getString(3);

        return new Merchant(id, cvr, name);
    }

    public List<Merchant> resultSetToMerchantList(ResultSet set) throws SQLException {
        List<Merchant> merchants = null;
        set.beforeFirst();

        while(set.next()) {
            int id = set.getInt(1);
            String cvr = set.getString(2);
            String name = set.getString(3);
            merchants.add(new Merchant(id, cvr, name));
        }
        return merchants;
    }
}

package com.merchant.database.helper;

import com.merchant.app.Merchant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * defined class which contains functions to get a new merchant with information from result sets.
 */

public class MerchantResultSetToObject {

    /**
     * gives information about merchant  from result set
     * @param set
     * @return new merchant
     * @throws SQLException
     */

    public Merchant resultSetToMerchant(ResultSet set) throws SQLException {
        set.beforeFirst();
        set.next();

        int id = set.getInt(1);
        String cvr = set.getString(2);
        String name = set.getString(3);

        return new Merchant(id, cvr, name);
    }

    /**
     * gives list which consists information in result set
     * @param set
     * @return list of merchant
     * @throws SQLException
     */

    public List<Merchant> resultSetToListOfMerchants(ResultSet set) throws SQLException {
        List<Merchant> merchantList = new ArrayList<>();

        while(set.next()) {
            int id = set.getInt(1);
            String cvr = set.getString(2);
            String name = set.getString(3);

            Merchant newMerchant = new Merchant(id, cvr, name);
            merchantList.add(newMerchant);
        }

        return merchantList;
    }
}

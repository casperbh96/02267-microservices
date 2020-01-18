package com.dtupay.database.helper;

import com.dtupay.app.Transaction;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionResultSetToObject {
    public Transaction resultSetToTransaction(ResultSet set) throws SQLException {
        int id = set.getInt(1);
        Timestamp timestamp = set.getTimestamp(2);
        int fromId = set.getInt(3);
        int toId = set.getInt(4);
        BigDecimal amount = set.getBigDecimal(5);
        int tokenId = set.getInt(6);
        boolean isRefund = set.getBoolean(7);

        return new Transaction(id, timestamp, fromId, toId, amount, tokenId, isRefund);
    }
}

package com.dtupay.app;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction extends TransactionCustomer{

    private int fromId;

    public Transaction(int id, Timestamp timestamp, BigDecimal amount, int tokenId, boolean isRefund, int toId, int fromId) {
        super(id, timestamp, amount, tokenId, isRefund, toId);
        this.fromId = fromId;
    }

    public int getFromId() {
        return fromId;
    }

}

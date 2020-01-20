package com.dtupay.app;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionCustomer extends Transaction {

    private int toId;

    public TransactionCustomer(int id, Timestamp timestamp, BigDecimal amount, int tokenId, boolean isRefund, int toId) {
        super(id, timestamp, amount, tokenId, isRefund);
        this.toId = toId;
    }

    public int getToId() {
        return toId;
    }
}

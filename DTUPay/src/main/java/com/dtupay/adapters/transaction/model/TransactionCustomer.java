package com.dtupay.adapters.transaction.model;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class TransactionCustomer extends Transaction {

    private int toId;

    public TransactionCustomer(JSONObject json) {
        super(json.getInt("id"), new Timestamp(json.getLong("timestamp")), json.getBigDecimal("amount"), json.getInt("tokenId"), json.getBoolean("refund"));
        this.toId = json.getInt("toId");
    }

    public TransactionCustomer(int id, Timestamp timestamp, BigDecimal amount, int tokenId, boolean isRefund, int toId) {
        super(id, timestamp, amount, tokenId, isRefund);
        this.toId = toId;
    }

    public int getToId() {
        return toId;
    }
}

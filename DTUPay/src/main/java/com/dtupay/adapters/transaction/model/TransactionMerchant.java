package com.dtupay.adapters.transaction.model;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionMerchant extends Transaction {

    public TransactionMerchant(JSONObject json) {
        super(json.getInt("id"), new Timestamp(json.getLong("timestamp")), json.getBigDecimal("amount"), json.getInt("tokenId"), json.getBoolean("refund"));
    }

    public TransactionMerchant(int id, Timestamp timestamp, BigDecimal amount, int tokenId, boolean isRefund) {
        super(id, timestamp, amount, tokenId, isRefund);
    }
}

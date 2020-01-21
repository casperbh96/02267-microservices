package com.dtupay.adapters.transaction.model;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * class for merchant transactions
 */
public class TransactionMerchant extends Transaction {

    /**
     * retrieving data from for merchant transactions from json file
     * @param json
     */
    public TransactionMerchant(JSONObject json) {
        super(json.getInt("id"), new Timestamp(json.getLong("timestamp")), json.getBigDecimal("amount"), json.getInt("tokenId"), json.getBoolean("refund"));
    }

    /**
     * transaction merchant initialization
     * @param id
     * @param timestamp
     * @param amount
     * @param tokenId
     * @param isRefund
     * @param toId
     */
    public TransactionMerchant(int id, Timestamp timestamp, BigDecimal amount, int tokenId, boolean isRefund) {
        super(id, timestamp, amount, tokenId, isRefund);
    }
}

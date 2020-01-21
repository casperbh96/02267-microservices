package com.dtupay.adapters.transaction.model;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author Dumitru
 * class for customer transactions
 */
public class TransactionCustomer extends Transaction {

    private int toId;

    /**
     * retrieving data for customer transactions from json file
     * @param json
     */
    public TransactionCustomer(JSONObject json) {
        super(json.getInt("id"), new Timestamp(json.getLong("timestamp")), json.getBigDecimal("amount"), json.getInt("tokenId"), json.getBoolean("refund"));
        this.toId = json.getInt("toId");
    }

    /**
     * transaction customer initialization
     * @param id
     * @param timestamp
     * @param amount
     * @param tokenId
     * @param isRefund
     * @param toId
     */
    public TransactionCustomer(int id, Timestamp timestamp, BigDecimal amount, int tokenId, boolean isRefund, int toId) {
        super(id, timestamp, amount, tokenId, isRefund);
        this.toId = toId;
    }

    /**
     * get id of the payment reciever
     * @return toId
     */
    public int getToId() {
        return toId;
    }
}

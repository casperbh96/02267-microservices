package com.transaction.app;


import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * created a transaction customer class which holds the transactions for customers
 */
public class TransactionCustomer extends Transaction {

    private int toId;

    /**
     * function for customer transactions
     * @param id of transaction
     * @param timestamp
     * @param amount to be transferred
     * @param tokenId
     * @param isRefund
     * @param toId
     */
    public TransactionCustomer(int id, Timestamp timestamp, BigDecimal amount, int tokenId, boolean isRefund, int toId) {
        super(id, timestamp, amount, tokenId, isRefund);
        this.toId = toId;
    }

    /**
     * shows to which id transaction should be made
     * @return toId
     */
    public int getToId() {
        return toId;
    }
}

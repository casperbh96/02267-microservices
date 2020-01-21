package com.dtupay.adapters.transaction.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * transaction class holds all sort of transactions
 */
public class Transaction {

    private int id;
    private Timestamp timestamp;
    private BigDecimal amount;
    private int tokenId;
    private boolean isRefund;

    /**
     * this is the constructor class for transaction method
     * @param id
     * @param timestamp
     * @param amount
     * @param tokenId
     * @param isRefund
     */
    public Transaction(int id, Timestamp timestamp, BigDecimal amount, int tokenId, boolean isRefund) {
        this.id = id;
        this.timestamp = timestamp;
        this.amount = amount;
        this.tokenId = tokenId;
        this.isRefund = isRefund;
    }

    /**
     * gives transaction id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * gives timestamp for the transaction
     * @return timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * gives amount for the transaction
     * @return amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * gives token id for transaction
     * @return tokenId
     */
    public int getTokenId() {
        return tokenId;
    }

    /**
     * checks if money is refunded or not
     * @return
     */
    public boolean isRefund() {
        return isRefund;
    }

}

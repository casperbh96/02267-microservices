package com.transaction.app;


import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author Ionela
 * creates a transaction class
 */
public class Transaction {

    private int id;
    private Timestamp timestamp;
    private BigDecimal amount;
    private int tokenId;
    private boolean isRefund;

    /**
     * this is a contructor for transaction class
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
     * shows the transaction id
     * @return transaction id
     */
    public int getId() {
        return id;
    }

    /**
     * @return timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * shows the amount in transaction
     * @return amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * shows the customer token id with transaction
     * @return tokenid
     */
    public int getTokenId() {
        return tokenId;
    }

    /**
     * checks if is refund or not
     * @return isRefund
     */
    public boolean isRefund() {
        return isRefund;
    }

}

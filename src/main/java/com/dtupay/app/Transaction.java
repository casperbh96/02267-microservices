package com.dtupay.app;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {

    private int id;
    private Timestamp timestamp;
    private int fromId;
    private int toId;
    private BigDecimal amount;
    private String tokenId;
    private boolean isRefund;

    public Transaction(int id, Timestamp timestamp, int fromId, int toId, BigDecimal amount, String tokenId, boolean isRefund) {
        this.id = id;
        this.timestamp = timestamp;
        this.fromId = fromId;
        this.toId = toId;
        this.amount = amount;
        this.tokenId = tokenId;
        this.isRefund = isRefund;
    }

    public int getId() {
        return id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getFromId() {
        return fromId;
    }

    public int getToId() {
        return toId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}

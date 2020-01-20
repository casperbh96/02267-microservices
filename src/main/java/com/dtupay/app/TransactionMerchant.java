package com.dtupay.app;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

public class TransactionMerchant {
    private int id;
    private Timestamp timestamp;
    private BigDecimal amount;
    private int tokenId;
    private boolean isRefund;

    public TransactionMerchant(int id, Timestamp timestamp, BigDecimal amount, int tokenId, boolean isRefund) {
        this.id = id;
        this.timestamp = timestamp;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public int getTokenId() {
        return tokenId;
    }

    public boolean isRefund() {
        return isRefund;
    }
}

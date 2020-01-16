package com.dtupay.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Transaction {

    private Timestamp timestamp;
    private String customerCpr;
    private String merchantCpr;
    private String tokenId;
    private BigDecimal amount;

    public Transaction(String customerCpr, String merchantCpr, String tokenId, BigDecimal amount) {
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
        this.customerCpr = customerCpr;
        this.merchantCpr = merchantCpr;
        this.tokenId = tokenId;
        this.amount = amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getCustomerCpr() {
        return customerCpr;
    }

    public String getMerchantCpr() {
        return merchantCpr;
    }

    public String getTokenId() {
        return tokenId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}

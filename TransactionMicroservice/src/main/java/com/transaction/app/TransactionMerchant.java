package com.transaction.app;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionMerchant extends Transaction {

    public TransactionMerchant(int id, Timestamp timestamp, BigDecimal amount, int tokenId, boolean isRefund) {
        super(id, timestamp, amount, tokenId, isRefund);
    }
}

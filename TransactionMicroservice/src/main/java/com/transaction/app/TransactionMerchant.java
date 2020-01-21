package com.transaction.app;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * created a transaction merchant class which holds the transactions for merchant
 */
public class TransactionMerchant extends Transaction {

    /**
     * function for merchant transactions
     * @param id
     * @param timestamp
     * @param amount to be transferred
     * @param tokenId
     * @param isRefund
     */
    public TransactionMerchant(int id, Timestamp timestamp, BigDecimal amount, int tokenId, boolean isRefund) {
        super(id, timestamp, amount, tokenId, isRefund);
    }
}

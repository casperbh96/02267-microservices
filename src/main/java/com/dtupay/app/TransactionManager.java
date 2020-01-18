package com.dtupay.app;

import com.dtupay.database.ITransactionAdapter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class TransactionManager implements ITransactionManager {

    private ITransactionAdapter transactionAdapter;

    public TransactionManager(ITransactionAdapter transactionAdapter) {
        this.transactionAdapter = transactionAdapter;
    }

    @Override
    public List<Transaction> getCustomerMonthlyReport(String customerCpr) {
        return null;
    }

    @Override
    public List<Transaction> getMerchantMonthlyReport(String merchantCpr) {
        return null;
    }

    @Override
    public Transaction registerTransaction(Timestamp timestamp, int fromId, int toId, int tokenId, BigDecimal amount, boolean isRefund) {
        return transactionAdapter.addTransaction(timestamp, fromId, toId, tokenId, amount, isRefund);
    }
}

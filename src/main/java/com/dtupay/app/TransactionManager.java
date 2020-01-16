package com.dtupay.app;

import com.dtupay.database.ITransactionAdapter;

import java.math.BigDecimal;
import java.util.List;

public class TransactionManager implements ITransactionManager {

    private ITransactionAdapter transactionAdapter;

    public TransactionManager(ITransactionAdapter transactionAdapter) {
        this.transactionAdapter = transactionAdapter;
    }

    @Override
    public List<String> getCustomerMonthlyReport(String customerCpr) {
        return null;
    }

    @Override
    public List<String> getMerchantMonthlyReport(String merchantCpr) {
        return null;
    }

    @Override
    public Transaction registerTransaction(String customerCpr, String merchantCpr, String tokenId, BigDecimal amount) {
        return transactionAdapter.addTransaction(customerCpr, merchantCpr, tokenId, amount);
    }
}

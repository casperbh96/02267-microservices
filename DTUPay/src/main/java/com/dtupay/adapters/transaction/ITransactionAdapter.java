package com.dtupay.adapters.transaction;

import com.dtupay.adapters.transaction.exceptions.TransactionException;
import com.dtupay.adapters.transaction.model.TransactionCustomer;
import com.dtupay.adapters.transaction.model.TransactionMerchant;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface ITransactionAdapter {
    List<TransactionCustomer> getMonthlyCustomerReport(int customerId, int month, int year) throws TransactionException;

    List<TransactionMerchant> getMonthlyMerchantReport(int merchantId, int month, int year) throws TransactionException;

    void registerTransaction(Timestamp timestamp, int fromId, int toId, int tokenId, BigDecimal amount, boolean isRefund) throws TransactionException;
}

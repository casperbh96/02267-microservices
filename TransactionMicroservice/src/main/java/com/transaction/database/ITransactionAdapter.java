package com.transaction.database;

import com.transaction.app.Transaction;
import com.transaction.app.TransactionCustomer;
import com.transaction.app.TransactionMerchant;
import com.transaction.database.exceptions.CustomerDoesNotExist;
import com.transaction.database.exceptions.TransactionDoesNotExist;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * interface for transaction adapter
 */
public interface ITransactionAdapter {
    Transaction addTransaction(Timestamp timestamp, int from, int to, int tokenId, BigDecimal amount, boolean isRefund);
    Transaction getTransactionByTransactionId(int id) throws TransactionDoesNotExist;
    List<TransactionCustomer> getMonthlyTransactionsByCustomerId(int customerId, int month, int year) throws CustomerDoesNotExist;
    List<TransactionMerchant> getMonthlyTransactionsByMerchantId(int merchantId, int month, int year);
}

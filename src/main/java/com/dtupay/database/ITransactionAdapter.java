package com.dtupay.database;

import com.dtupay.app.Transaction;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.exceptions.TransactionDoesNotExist;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface ITransactionAdapter {
    Transaction addTransaction(Timestamp timestamp, int from, int to, int tokenId, BigDecimal amount, boolean isRefund);
    Transaction getTransactionByTransactionId(int id) throws TransactionDoesNotExist;
    List<Transaction> getMonthlyTransactionsByCustomerId(int customerId, int month, int year) throws CustomerDoesNotExist;
    List<Transaction> getMonthlyTransactionsByMerchantId(int merchantId, int month, int year);
}

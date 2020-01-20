package com.dtupay.database;

import com.dtupay.app.Transaction;
import com.dtupay.app.TransactionCustomer;
import com.dtupay.app.TransactionMerchant;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.exceptions.TransactionDoesNotExist;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface ITransactionAdapter {
    Transaction addTransaction(Timestamp timestamp, int from, int to, int tokenId, BigDecimal amount, boolean isRefund);
    Transaction getTransactionByTransactionId(int id) throws TransactionDoesNotExist;
    List<TransactionCustomer> getMonthlyTransactionsByCustomerId(int customerId, int month, int year) throws CustomerDoesNotExist;
    List<TransactionMerchant> getMonthlyTransactionsByMerchantId(int merchantId, int month, int year);
}

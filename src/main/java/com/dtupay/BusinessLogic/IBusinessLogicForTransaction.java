package com.dtupay.BusinessLogic;

import com.dtupay.app.Transaction;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface IBusinessLogicForTransaction {
    List<Transaction> getTransactionsByCustomerId(int customerId);
    Transaction getTransactionByTransactionId(int id);
    Transaction addTransaction(Timestamp timestamp, int from, int to, int tokenId, BigDecimal amount, boolean isRefund);
}

package com.transaction.manager;

import com.transaction.app.Transaction;
import com.transaction.app.TransactionCustomer;
import com.transaction.app.TransactionMerchant;
import com.transaction.database.exceptions.CustomerDoesNotExist;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * defines interface for Transaction manager which contains three functions about transaction information
 */
public interface ITransactionManager {

    List<TransactionCustomer> getCustomerMonthlyReport(int customerId, int month, int year) throws CustomerDoesNotExist;

    List<TransactionMerchant> getMerchantMonthlyReport(int merchantId, int month, int year);

    Transaction registerTransaction(Timestamp timestamp, int fromId, int toId, int tokenId, BigDecimal amount, boolean isRefund);
}

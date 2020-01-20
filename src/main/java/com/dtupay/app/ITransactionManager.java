package com.dtupay.app;

import com.dtupay.database.exceptions.CustomerDoesNotExist;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface ITransactionManager {
    List<TransactionCustomer> getCustomerMonthlyReport(int customerId, int month, int year) throws CustomerDoesNotExist;

    List<TransactionMerchant> getMerchantMonthlyReport(int merchantId, int month, int year);

    Transaction registerTransaction(Timestamp timestamp, int fromId, int toId, int tokenId, BigDecimal amount, boolean isRefund);
}

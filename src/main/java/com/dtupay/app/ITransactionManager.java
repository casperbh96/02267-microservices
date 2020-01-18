package com.dtupay.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface ITransactionManager {
    List<Transaction> getCustomerMonthlyReport(int customerId, int month, int year);

    List<Transaction> getMerchantMonthlyReport(int merchantId, int month, int year);

    Transaction registerTransaction(Timestamp timestamp, int fromId, int toId, int tokenId, BigDecimal amount, boolean isRefund);
}

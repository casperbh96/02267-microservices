package com.dtupay.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface ITransactionManager {
    List<Transaction> getCustomerMonthlyReport(String customerCpr);
    List<Transaction> getMerchantMonthlyReport(String merchantCpr);
    Transaction registerTransaction(Timestamp timestamp, int fromId, int toId, int tokenId, BigDecimal amount, boolean isRefund);
}

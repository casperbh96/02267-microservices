package com.dtupay.app;

import java.math.BigDecimal;
import java.util.List;

public interface ITransactionManager {
    List<String> getCustomerMonthlyReport(String customerCpr);
    List<String> getMerchantMonthlyReport(String merchantCpr);
    Transaction registerTransaction(String customerCpr, String merchantCpr, String tokenId, BigDecimal amount);
}

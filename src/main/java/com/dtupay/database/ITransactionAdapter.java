package com.dtupay.database;

import com.dtupay.app.Transaction;
import com.dtupay.database.exceptions.CustomerDoesNotExist;

import java.math.BigDecimal;
import java.util.List;

public interface ITransactionAdapter {
    List<Transaction> getTransactionsByCustomer(String customerCpr) throws CustomerDoesNotExist;
    Transaction addTransaction(String customerCpr, String merchantCpr, String tokenId, BigDecimal amount);
}

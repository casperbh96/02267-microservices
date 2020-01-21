package com.transaction.manager;

import com.transaction.database.ITransactionAdapter;
import com.transaction.database.exceptions.CustomerDoesNotExist;
import com.transaction.app.Transaction;
import com.transaction.app.TransactionCustomer;
import com.transaction.app.TransactionMerchant;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * created a transaction manager class to hold all the transactions
 */
public class TransactionManager implements ITransactionManager {

    private ITransactionAdapter transactionAdapter;

    /**
     * constructor for transaction manager
     * @param transactionAdapter
     */
    public TransactionManager(ITransactionAdapter transactionAdapter) {
        this.transactionAdapter = transactionAdapter;
    }

    /**
     * holds a list of transaction for every customer
     * @param customerId
     * @param month for which transaction holds
     * @param year for which transaction holds
     * @return list of transactions
     * @throws CustomerDoesNotExist exception
     */
    @Override
    public List<TransactionCustomer> getCustomerMonthlyReport(int customerId, int month, int year) throws CustomerDoesNotExist {
        return transactionAdapter.getMonthlyTransactionsByCustomerId(customerId, month, year);
    }

    /**
     * holds a list of transaction for every merchant
     * @param merchantId
     * @param month for which transaction holds
     * @param year for which transaction holds
     * @return list of transactions
     */
    @Override
    public List<TransactionMerchant> getMerchantMonthlyReport(int merchantId, int month, int year) {

        return transactionAdapter.getMonthlyTransactionsByMerchantId(merchantId, month, year);
    }

    /**
     * funcition to register all the transactions
     * @param timestamp
     * @param fromId
     * @param toId
     * @param tokenId
     * @param amount
     * @param isRefund
     * @return transactions
     */
    @Override
    public Transaction registerTransaction(Timestamp timestamp, int fromId, int toId, int tokenId, BigDecimal amount, boolean isRefund) {
        return transactionAdapter.addTransaction(timestamp, fromId, toId, tokenId, amount, isRefund);
    }
}

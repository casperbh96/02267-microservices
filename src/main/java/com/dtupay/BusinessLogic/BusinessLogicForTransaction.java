package com.dtupay.BusinessLogic;

import com.dtupay.app.Transaction;
import com.dtupay.database.ITransactionAdapter;
import com.dtupay.database.TransactionAdapter;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.exceptions.TransactionDoesNotExist;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BusinessLogicForTransaction implements IBusinessLogicForTransaction {

    ITransactionAdapter transactionAdapter = new TransactionAdapter();

    public List<Transaction> getTransactionsByCustomerId(int customerId) {
        try {
            return transactionAdapter.getTransactionsByCustomerId(customerId);
        } catch (CustomerDoesNotExist customerDoesNotExist) {
            customerDoesNotExist.printStackTrace();
            return null;
        }
    }


    public Transaction getTransactionByTransactionId(int id) {
        try {
            return transactionAdapter.getTransactionByTransactionId(id);
        } catch (TransactionDoesNotExist transactionDoesNotExist) {
            transactionDoesNotExist.printStackTrace();
            return null;
        }
    }


    public Transaction addTransaction(Timestamp timestamp, int from, int to, String tokenId, BigDecimal amount, boolean isRefund) {
        return transactionAdapter.addTransaction(timestamp, from, to, tokenId, amount, isRefund);
    }
}

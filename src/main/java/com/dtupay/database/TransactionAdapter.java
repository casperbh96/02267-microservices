package com.dtupay.database;

import com.dtupay.app.Transaction;
import com.dtupay.database.exceptions.CustomerDoesNotExist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionAdapter implements ITransactionAdapter {

    private List<Transaction> transactions;

    public TransactionAdapter() {
        transactions = new ArrayList<>();
        //add some sample transactions here if needed
    }

    @Override
    public List<Transaction> getTransactionsByCustomer(String customerCpr) throws CustomerDoesNotExist {
        return transactions.stream().filter(t -> t.getCustomerCpr().equals(customerCpr)).collect(Collectors.toList());
    }

    @Override
    public Transaction addTransaction(String customerCpr, String merchantCpr, String tokenId, BigDecimal amount) {
        Transaction transaction = new Transaction(customerCpr, merchantCpr, tokenId, amount);
        transactions.add(transaction);
        return transaction;
    }

}

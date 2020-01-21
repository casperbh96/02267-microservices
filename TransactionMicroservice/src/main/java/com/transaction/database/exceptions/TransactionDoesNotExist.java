package com.transaction.database.exceptions;

/**
 * @author Ionela
 * transaction exception class called when there is no transaction
 */
public class TransactionDoesNotExist extends Exception {
    public TransactionDoesNotExist(String message) {
        super(message);
    }
}

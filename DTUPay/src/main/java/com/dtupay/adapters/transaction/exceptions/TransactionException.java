package com.dtupay.adapters.transaction.exceptions;

/**
 * exception class used to call at the transaction failures
 */
public class TransactionException extends Exception {
    public TransactionException(String errorMessage) {
        super(errorMessage);
    }
}

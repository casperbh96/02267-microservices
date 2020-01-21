package com.dtupay.adapters.transaction.exceptions;

/**
 * @author Dumitru
 * exception class used to call at the transaction failures
 */
public class TransactionException extends Exception {
    public TransactionException(String errorMessage) {
        super(errorMessage);
    }
}

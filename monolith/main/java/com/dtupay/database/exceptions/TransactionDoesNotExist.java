package com.dtupay.database.exceptions;

public class TransactionDoesNotExist extends Exception {
    public TransactionDoesNotExist(String message) {
        super(message);
    }
}

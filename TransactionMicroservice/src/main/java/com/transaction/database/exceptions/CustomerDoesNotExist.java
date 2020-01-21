package com.transaction.database.exceptions;

/**
 * exception class for customer not in list
 */
public class CustomerDoesNotExist extends Exception {
    public CustomerDoesNotExist(String errorMessage) {
        super(errorMessage);
    }
}

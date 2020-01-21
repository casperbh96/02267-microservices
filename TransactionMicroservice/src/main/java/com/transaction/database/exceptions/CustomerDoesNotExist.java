package com.transaction.database.exceptions;

/**
 * @author Ionela
 * exception class for customer not in list
 */
public class CustomerDoesNotExist extends Exception {
    public CustomerDoesNotExist(String errorMessage) {
        super(errorMessage);
    }
}

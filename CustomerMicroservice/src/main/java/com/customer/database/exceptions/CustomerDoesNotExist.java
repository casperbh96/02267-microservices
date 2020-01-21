package com.customer.database.exceptions;

/**
 * Exception class for customer which throws an exception when customer does not exist
 */
public class CustomerDoesNotExist extends Exception {
    public CustomerDoesNotExist(String errorMessage) {
        super(errorMessage);
    }
}

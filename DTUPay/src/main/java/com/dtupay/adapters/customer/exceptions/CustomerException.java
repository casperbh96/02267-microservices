package com.dtupay.adapters.customer.exceptions;

/**
 * exception for customer used in customer adapter
 */
public class CustomerException extends Exception {
    public CustomerException(String errorMessage) {
        super(errorMessage);
    }
}

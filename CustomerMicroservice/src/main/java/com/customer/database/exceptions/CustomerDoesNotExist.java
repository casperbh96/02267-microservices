package com.customer.database.exceptions;

public class CustomerDoesNotExist extends Exception {
    public CustomerDoesNotExist(String errorMessage) {
        super(errorMessage);
    }
}

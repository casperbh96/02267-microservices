package com.dtupay.database.exceptions;

public class CustomerHasNoUnusedToken extends Exception {
    public CustomerHasNoUnusedToken(String errorMessage) {
        super(errorMessage);
    }
}

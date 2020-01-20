package com.token.database.exceptions;

public class CustomerHasNoUnusedToken extends Exception {
    public CustomerHasNoUnusedToken(String errorMessage) {
        super(errorMessage);
    }
}

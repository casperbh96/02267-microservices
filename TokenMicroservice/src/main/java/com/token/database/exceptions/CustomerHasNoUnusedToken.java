package com.token.database.exceptions;

/**
 * Exception class for customer which throws an exception when customer does not have any unused token
 */

public class CustomerHasNoUnusedToken extends Exception {
    public CustomerHasNoUnusedToken(String errorMessage) {
        super(errorMessage);
    }
}

package com.token.database.exceptions;

/**
 * exception class for the time when customers is requesting more tokens than he can
 */
public class TooManyTokenRequest extends Exception {
    public TooManyTokenRequest(String errorMessage) { super(errorMessage); }
}

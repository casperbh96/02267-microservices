package com.token.database.exceptions;

/**
 * exception class for the time when customer has enough tokens
 */
public class CustomerIsUnableToReceiveNewTokens extends Exception {
    public CustomerIsUnableToReceiveNewTokens(String errorMessage) { super(errorMessage); }
}

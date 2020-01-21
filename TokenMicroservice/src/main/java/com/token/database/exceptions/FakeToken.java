package com.token.database.exceptions;

/**
 * exception class for invalid tokens
 */
public class FakeToken extends Exception {
    public FakeToken(String errorMessage) {
        super(errorMessage);
    }
}

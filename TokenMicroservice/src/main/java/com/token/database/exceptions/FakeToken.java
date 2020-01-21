package com.token.database.exceptions;

/**
 * @author Casper
 * exception class for invalid tokens
 */
public class FakeToken extends Exception {
    public FakeToken(String errorMessage) {
        super(errorMessage);
    }
}

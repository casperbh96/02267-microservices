package com.merchant.database.exceptions;

/**
 * created exception class for faken token
 */
public class FakeToken extends Exception {
    public FakeToken(String errorMessage) {
        super(errorMessage);
    }
}

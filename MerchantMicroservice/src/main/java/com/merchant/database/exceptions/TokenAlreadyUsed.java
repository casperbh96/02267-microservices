package com.merchant.database.exceptions;

/**
 * created exception class for token is already used
 */
public class TokenAlreadyUsed extends Exception {
    public TokenAlreadyUsed(String errorMessage) {
        super(errorMessage);
    }
}

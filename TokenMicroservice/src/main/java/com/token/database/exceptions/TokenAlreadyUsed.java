package com.token.database.exceptions;

/**
 * exception class for tokens which are already used
 */
public class TokenAlreadyUsed extends Exception {
    public TokenAlreadyUsed(String errorMessage) {
        super(errorMessage);
    }
}

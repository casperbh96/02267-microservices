package com.calculator.database;

public class TokenAlreadyUsed extends Exception {
    public TokenAlreadyUsed(String errorMessage) {
        super(errorMessage);
    }
}

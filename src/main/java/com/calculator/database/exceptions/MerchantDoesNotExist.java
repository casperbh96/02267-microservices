package com.calculator.database.exceptions;

public class MerchantDoesNotExist extends Exception {
    public MerchantDoesNotExist(String errorMessage) {
        super(errorMessage);
    }
}

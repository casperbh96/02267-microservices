package com.dtupay.database.exceptions;

public class TooManyTokenRequest extends Exception {
    public TooManyTokenRequest(String errorMessage) { super(errorMessage); }
}

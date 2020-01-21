package com.dtupay.adapters.bank.exceptions;

/**
 * @author Ionela
 * exception for bank adapter which will thrown whenever there is any bank transaction problme appears
 */
public class BankAdapterException extends Exception {
    public BankAdapterException(String errorMessage) {
        super(errorMessage);
    }
}

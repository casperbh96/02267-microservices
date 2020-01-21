package com.merchant.database.exceptions;

/**
 * @author Danial
 * created exception class for merchant does not exist
 */
public class MerchantDoesNotExist extends Exception {
    public MerchantDoesNotExist(String errorMessage) {
        super(errorMessage);
    }
}

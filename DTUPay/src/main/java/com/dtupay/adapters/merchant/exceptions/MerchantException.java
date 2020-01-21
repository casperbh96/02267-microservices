package com.dtupay.adapters.merchant.exceptions;

/**
 * exception for customer used in customer adapter
 */

public class MerchantException extends Exception {
    public MerchantException(String errorMessage) {
        super(errorMessage);
    }
}

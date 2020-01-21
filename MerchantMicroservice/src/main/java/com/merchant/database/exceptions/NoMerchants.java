package com.merchant.database.exceptions;

/**
 * @author Danial
 * created exeception class for having no merchant
 */
public class NoMerchants extends Exception{
    public NoMerchants(String errorMessage) { super(errorMessage); }
}

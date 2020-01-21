package com.customer.database.exceptions;

/**
 * @author Dumitru
 * Exception class for customer which throws an exception when there are no customers
 */

public class NoCustomers extends Exception{
    public NoCustomers(String errorMessage) {
        super(errorMessage);
    }

}

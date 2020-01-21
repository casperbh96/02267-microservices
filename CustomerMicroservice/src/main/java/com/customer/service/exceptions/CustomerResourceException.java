package com.customer.service.exceptions;

import javax.ws.rs.core.Response;

/**
 * @author Danial
 * exception class to return bad request for rest service for customer
 */
public class CustomerResourceException extends Exception{
    public CustomerResourceException() {
        Response.status(400).build();
    }
}

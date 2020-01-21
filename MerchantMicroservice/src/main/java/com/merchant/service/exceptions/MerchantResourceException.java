package com.merchant.service.exceptions;

import javax.ws.rs.core.Response;

/**
 * @author Ismael
 * exception class to return bad request for rest service for merchant
 */
public class MerchantResourceException extends Exception{
    public MerchantResourceException() {
        Response.status(400).build();
    }
}

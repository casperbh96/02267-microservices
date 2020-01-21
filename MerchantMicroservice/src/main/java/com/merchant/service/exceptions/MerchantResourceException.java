package com.merchant.service.exceptions;

import javax.ws.rs.core.Response;

public class MerchantResourceException extends Exception{
    public MerchantResourceException() {
        Response.status(400).build();
    }
}

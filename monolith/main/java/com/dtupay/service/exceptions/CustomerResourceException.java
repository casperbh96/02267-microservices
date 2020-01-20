package com.dtupay.service.exceptions;

import javax.ws.rs.core.Response;

public class CustomerResourceException extends Exception{
    public CustomerResourceException() {
        Response.status(400).build();
    }
}

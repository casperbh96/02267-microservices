package com.dtupay.service;

import com.dtupay.BusinessLogic.BusinessLogicForTransaction;
import com.dtupay.BusinessLogic.IBusinessLogicForTransaction;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/transaction")
@Produces("application/json")
@Consumes("application/json")
public class TransactionResource {
    private static IBusinessLogicForTransaction t = new BusinessLogicForTransaction();

    
}

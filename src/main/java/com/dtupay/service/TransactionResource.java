package com.dtupay.service;

import com.dtupay.app.ITransactionManager;
import com.dtupay.app.TransactionManager;
import com.dtupay.database.TransactionAdapter;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Path("/transaction")
@Produces("application/json")
@Consumes("application/json")
public class TransactionResource {
    ITransactionManager transactions = new TransactionManager(new TransactionAdapter());

    @GET
    @Path("customer/{id}")
    public Response getMonthlyReportForCustomer(@PathParam("id") int id) {
        if (id == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(transactions.getCustomerMonthlyReport(id)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("merchant/{id}")
    public Response getMonthlyReportForMerchant(@PathParam("id") int id) {
        if (id == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(transactions.getMerchantMonthlyReport(id)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Consumes("application/json")
    public Response postNewToken(String jsonRaw) {
        JSONObject json = new JSONObject(jsonRaw);

        Timestamp timestamp = Timestamp.valueOf(json.getString("timestamp"));
        int fromId = json.getInt("fromId");
        int toId = json.getInt("toId");
        int tokenId = json.getInt("tokenId");
        BigDecimal amount = json.getBigDecimal("amount");
        boolean isRefund = json.getBoolean("isRefund");

        if ((fromId == 0) || (toId == 0) || (tokenId == 0)) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(transactions.registerTransaction(timestamp, fromId, toId, tokenId, amount, isRefund)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

}

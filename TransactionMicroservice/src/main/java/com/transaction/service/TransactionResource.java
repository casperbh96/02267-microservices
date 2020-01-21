package com.transaction.service;

import com.transaction.manager.ITransactionManager;
import com.transaction.manager.TransactionManager;
import com.transaction.database.TransactionAdapter;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * defining path for transaction
 */
@Path("/transaction")

/**
 * to produce file in json format
 */
@Produces("application/json")
@Consumes("application/json")

/**
 * class for transaction using rest services
 */
public class TransactionResource {
    ITransactionManager transactions = new TransactionManager(new TransactionAdapter());

    /**
     * gives monthly report of transactions for customer
     * @param jsonRaw
     * @return response with list of transactions
     */
    @POST
    @Path("customer")
    @Consumes("application/json")

    public Response getMonthlyReportForCustomer(String jsonRaw) {
        JSONObject json = new JSONObject(jsonRaw);

        int id = json.getInt("id");
        int month = json.getInt("month");
        int year = json.getInt("year");

        if (id == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(transactions.getCustomerMonthlyReport(id, month, year)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    e.getMessage()).build();
        }
    }

    /**
     * gives monthly report of transactions for merchant
     * @param jsonRaw
     * @return response with list of transactions
     */
    @POST
    @Path("merchant")
    @Consumes("application/json")
    public Response getMonthlyReportForMerchant(String jsonRaw) {
        JSONObject json = new JSONObject(jsonRaw);

        int id = json.getInt("id");
        int month = json.getInt("month");
        int year = json.getInt("year");

        if (id == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(transactions.getMerchantMonthlyReport(id, month, year)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    e.getMessage()).build();
        }
    }

    /**
     * add up the new transactions
     * @param jsonRaw
     * @return response with registering new transaction
     */
    @POST
    @Consumes("application/json")
    public Response postNewTransaction(String jsonRaw) {
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    e.getMessage()).build();
        }
    }

}

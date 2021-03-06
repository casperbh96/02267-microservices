package com.dtupay.service;

import com.dtupay.DTUPayApp;
import com.dtupay.IDTUPayApp;
import com.dtupay.adapters.token.model.Token;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;



/**
 * @author Dumitru
 * class for DTUPay using rest services
 */
@Path("/")
@Produces("application/json")
public class DTUPayResource {

    private static IDTUPayApp dtuPayApp = new DTUPayApp("http://customer:8080/customer/",
            "http://merchant:8080/merchant/",
            "http://transaction:8080/transaction/",
            "http://token:8080/token/");

    @POST
    @Path("customer")
    @Consumes("application/json")

    /**
     * updates the customer using POST request
     * @param json
     * @return the update response
     */
    public Response postCustomer(String json) {
        JSONObject obj = new JSONObject(json);
        String cpr = obj.getString("cpr");
        String name = obj.getString("name");

        if (name == null || cpr == null) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(dtuPayApp.createCustomer(cpr, name)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    e.getMessage()).build();
        }
    }

    /**
     * updates the customer using PUT request
     * @param json
     * @return the update response
     */
    @PUT
    @Path("customer")
    @Consumes("application/json")
    public Response putCustomer(String json) {
        JSONObject obj = new JSONObject(json);
        int id = obj.getInt("id");
        String cpr = obj.getString("cpr");
        String name = obj.getString("name");

        if (cpr == null || id == 0 || name == null) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(dtuPayApp.updateCustomer(id, cpr, name)).build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }

    /**
     * gives new tokens to customer using REST service
     * @param jsonRaw
     * @return response request
     */

    @POST
    @Path("newTokens")
    @Consumes("application/json")

    public Response getNewTokens(String jsonRaw) {
        JSONObject json = new JSONObject(jsonRaw);
        int customerId = json.getInt("customerId");
        int numberOfTokens = json.getInt("numberOfTokens");

        try {
            return Response.ok(dtuPayApp.getMoreTokens(customerId, numberOfTokens)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    e.getMessage()).build();
        }
    }

    /**
     * gives monthly report of transactions for customer
     * @param jsonRaw
     * @return response monthy report(list type)
     */
    @POST
    @Path("customer/transactions")
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
            return Response.ok(dtuPayApp.generateMonthlyCustomerReport(id, month, year)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    /**
     * updates the merchant using POST request
     * @param json
     * @return the update response
     */
    @POST
    @Path("merchant")
    @Consumes("application/json")
    public Response postMerchant(String json) {
        JSONObject obj = new JSONObject(json);
        String cvr = obj.getString("cvr");
        String name = obj.getString("name");

        if (cvr == null || name == null) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.accepted(dtuPayApp.createMerchant(cvr, name)).build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }

    /**
     * updates the merchant using PUT request
     * @param json
     * @return the update response
     */
    @PUT
    @Path("merchant")
    @Consumes("application/json")
    public Response putMerchant(String json) {
        JSONObject obj = new JSONObject(json);
        int id = obj.getInt("id");
        String cvr = obj.getString("cvr");
        String name = obj.getString("name");

        if (cvr == null || id == 0 || name == null) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(dtuPayApp.updateMerchant(id, cvr, name)).build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }

    /**
     * returns transaction information
     * @param jsonRaw
     * @return response for transaction
     */
    @POST
    @Path("transaction")
    @Consumes("application/json")
    public Response requestTransaction(String jsonRaw) {
        JSONObject json = new JSONObject(jsonRaw);

        int merchantId = json.getInt("merchantId");
        Token token = new Token(json.getJSONObject("token"));
        BigDecimal amount = json.getBigDecimal("amount");
        String description = json.getString("description");

        if (merchantId == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            dtuPayApp.transferMoney(merchantId, token, amount, description);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    e.getMessage()).build();
        }
    }

    /**
     * gives monthly report of transactions for merchant
     * @param jsonRaw
     * @return response monthy report(list type)
     */
    @POST
    @Path("merchant/transactions")
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
            return Response.ok(dtuPayApp.generateMonthlyMerchantReport(id, month, year)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    e.getMessage()).build();
        }
    }
}

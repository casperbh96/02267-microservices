package com.dtupay.service;

import com.dtupay.BusinessLogic.BusinessLogicForMerchant;
import com.dtupay.BusinessLogic.IBusinessLogicForMerchant;
import org.json.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/merchant")
@Produces("application/json")
public class MerchantResource {

    private static IBusinessLogicForMerchant m = new BusinessLogicForMerchant();

    @GET
    public Response getMerchants() {
        try {
            return Response.ok(m.getAllMerchants()).build();
        }
        catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("{id}")
    public Response getMerchant(@PathParam("id") int id) {
        if (id == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            //return Response.ok("Get merchants").build();
            //return Response.status(200).entity(response).build();
            //return Response.status(200).entity("Get merchants").build();
            return Response.ok(m.getMerchantByMerchantId(id)).build();
        }
        catch(Exception e) {
            return Response.status(400).build();
        }
    }

    @POST
    @Consumes("application/json")
    public Response postMerchant(String json) {
        JSONObject obj = new JSONObject(json);
        String cvr = obj.getString("cvr");
        String name = obj.getString("name");

        if (cvr == null || name == null) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            //String response = sendMessage(message);
            //return Response.status(200).entity(response).build();
            return Response.accepted(m.createMerchant(cvr, name)).build();
        }
        catch(Exception e) {
            return Response.status(400).build();
        }
    }

    @PUT
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
            return Response.ok(m.updateMerchant(id, cvr, name)).build();
        }
        catch(Exception e) {
            return Response.status(400).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteMerchant(@PathParam("id") int id) {
        if (id == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            // String response = sendMessage(message);
            //return Response.ok("Deleted merchants").build();
            //return Response.status(200).entity(response).build();
            m.deleteMerchantByMerchantId(id);
            return Response.ok("Deleted merchants").build();
        }
        catch(Exception e) {
            return Response.status(400).build();
        }
    }
}


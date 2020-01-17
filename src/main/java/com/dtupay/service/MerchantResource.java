package com.dtupay.service;

import com.dtupay.app.Merchant;
import com.dtupay.database.IMerchantAdapter;
import com.dtupay.database.MerchantAdapter;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;



@Path("/merchant")
@Produces("application/json")
@Consumes("application/json")
public class MerchantResource {

    private static IMerchantAdapter m = new MerchantAdapter();

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
        if(id == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            //return Response.ok("Get merchants").build();
            //return Response.status(200).entity(response).build();
            //return Response.status(200).entity("Get merchants").build();
            return Response.ok(m.getMerchantByMerchantId(id)).build();
        }
        catch(Exception e) {
            return Response.serverError().build();
        }
    }

    @POST
    public Response postMerchant(Merchant merchant) {
        if(merchant.getName() == null || merchant.getId() == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            //String response = sendMessage(message);
            //return Response.status(200).entity(response).build();
            return Response.ok(m.createMerchant(merchant)).build();
        }
        catch(Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    public Response putCustomer(Merchant merchant) {
        if(merchant.getName() == null || merchant.getId() == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(m.updateMerchant(merchant)).build();
        }
        catch(Exception e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteMerchant(@PathParam("id") int id) {
        if(id == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            // String response = sendMessage(message);
            //return Response.ok("Deleted merchants").build();
            //return Response.status(200).entity(response).build();
            m.deleteMerchantByMerchantId(id);
            return Response.ok("Deleted merchants").status(200).build();
        }
        catch(Exception e) {
            return Response.serverError().build();
        }
    }
}


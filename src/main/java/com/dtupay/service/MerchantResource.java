/*
package com.dtupay.service;

import com.dtupay.app.Merchant;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/merchant")
public class MerchantResource {
    @GET
    @Produces("text/plain")
    public Response getMerchants() {
        return Response.ok("Get merchants").build();
    }

    @GET
    @Path("{merchant}")
    @Produces("text/plain")
    public Response getMerchant(Merchant merchant) {
        String message = "getMerchant/" + merchant;
        if(merchant == null) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            // String response = sendMessage(message);
            //return Response.ok("Get merchants").build();
            //return Response.status(200).entity(response).build();
            return Response.status(200).entity("Get merchants").build();
        }
        catch(Exception e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Produces("text/plain")
    public Response postMerchant(Merchant merchant) {
        String message = "postMerchant/" + merchant.getName() + "/" + merchant.getId();
        if(merchant.getName() == null || merchant.getId() == null) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            //String response = sendMessage(message);
            //return Response.status(200).entity(response).build();
            return Response.ok("Posted merchant").build();
        }
        catch(Exception e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("{merchant}")
    @Produces("text/plain")
    public Response deleteMerchant(Merchant merchant) {
        String message = "deleteMerchant/" + merchant;
        if(merchant == null) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            // String response = sendMessage(message);
            //return Response.ok("Deleted merchants").build();
            //return Response.status(200).entity(response).build();
            return Response.status(200).entity("Deleted merchants").build();
        }
        catch(Exception e) {
            return Response.serverError().build();
        }
    }
}
*/

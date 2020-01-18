package com.dtupay.service;

import com.dtupay.app.Token;
import com.dtupay.database.ITokenAdapter;
import com.dtupay.database.TokenAdapter;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/token")
@Produces("application/json")
@Consumes("application/json")
public class TokenResource {

    private static ITokenAdapter c = new TokenAdapter();

    @GET
    public Response getTokens() {
        try {
            return Response.ok(c.getAllTokens()).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @POST
    public Response postToken(int customerId, UUID uuid, boolean used) {
        if (customerId == 0 || uuid == null) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(c.createToken(customerId, uuid, used)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

}

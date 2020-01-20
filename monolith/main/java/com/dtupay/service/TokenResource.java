package com.dtupay.service;

import com.dtupay.BusinessLogic.TokenManager;
import com.dtupay.BusinessLogic.ITokenManager;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/token")
@Produces("application/json")
public class TokenResource {

    private static ITokenManager tokens = new TokenManager();

    @GET
    public Response getTokens() {
        try {
            return Response.ok(tokens.getAllTokens()).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("unused/{customerId}")
    public Response getUnusedToken(@PathParam("customerId") int customerId) {
        if (customerId == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(tokens.getUnusedTokenByCustomerId(customerId)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{tokenId}")
    public Response getValidityOfAToken(@PathParam("tokenId") int tokenId) {
        if (tokenId == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(tokens.isTokenValid(tokenId)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("{tokenId}")
    public Response putTokenToUsed(@PathParam("tokenId") int tokenId) {
        if (tokenId == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            tokens.markTokenAsUsed(tokenId);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }


    @POST
    @Consumes("application/json")
    public Response postNewToken(String jsonRaw) {
        JSONObject json = new JSONObject(jsonRaw);
        int customerId = json.getInt("customerId");
        UUID uuid = UUID.fromString(json.getString("uuid"));
        boolean used = json.getBoolean("used");

        if (customerId == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(tokens.createToken(customerId, uuid, used)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

}

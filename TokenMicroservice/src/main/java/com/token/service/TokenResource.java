package com.token.service;

import com.token.manager.TokenManager;
import com.token.manager.ITokenManager;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * @author Casper
 * class to handle tokens using rest services
 */
@Path("/token")
@Produces("application/json")
public class TokenResource {

    private static ITokenManager tokens = new TokenManager();

    /**
     * shows response about retrieval of information for all the tokens using GET request
     *
     * @return response
     */
    @GET
    public Response getTokens() {
        try {
            return Response.ok(tokens.getAllTokens()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    e.getMessage()).build();
        }
    }

    /**
     * sets a new path for get tokens
     * shows all the tokens from json file
     *
     * @param jsonRaw
     * @return response
     */
    @POST
    @Path("newTokens")
    @Consumes("application/json")
    public Response getTokens(String jsonRaw) {
        JSONObject json = new JSONObject(jsonRaw);
        int customerId = json.getInt("customerId");
        int numberOfTokens = json.getInt("numberOfTokens");

        try {
            return Response.ok(tokens.getTokensForCustomer(customerId, numberOfTokens)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    e.getMessage()).build();
        }
    }

    /**
     * set new path for getting unused token
     * shows unused tokens with respect to customer id
     *
     * @param customerId
     * @return response
     */

    @GET
    @Path("unused/{customerId}")
    public Response getUnusedToken(@PathParam("customerId") int customerId) {
        if (customerId == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(tokens.getUnusedTokenByCustomerId(customerId)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    e.getMessage()).build();
        }
    }

    /**
     * set new path for getting validity of token
     * shows if token is valid or not
     *
     * @param tokenId
     * @return response
     */
    @GET
    @Path("/{tokenId}")
    public Response getValidityOfAToken(@PathParam("tokenId") int tokenId) {
        if (tokenId == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(tokens.isTokenValid(tokenId)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    e.getMessage()).build();
        }
    }

    /**
     * gives a response about is token has been put to used
     *
     * @param tokenId
     * @return response
     */
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    e.getMessage()).build();
        }
    }

    /**
     * creates a new token and adds it to json file
     *
     * @param jsonRaw
     * @return response
     */
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    e.getMessage()).build();
        }
    }

}

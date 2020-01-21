package com.customer.service;

import com.customer.manager.CustomerManager;
import com.customer.manager.ICustomerManager;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * @author Danial
 * defines path for customer
 */
@Path("/customer")

/**
 * to produce file in json format
 */

@Produces("application/json")

/**
 * class to handle customer using rest services
 */
public class CustomerResource {

    private static ICustomerManager c = new CustomerManager();

    /**
     * shows response about retrieval of information for all customers using GET request
     * @return response
     */
    @GET
    public Response getCustomers() {
        try {
            return Response.ok(c.GetAllCustomers()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    e.getMessage()).build();
        }
    }

    /**
     * shows information about customer of a particular id using GET request
     * @param id
     * @return response for a customer
     */
    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id") int id) {
        if (id == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(c.GetCustomerByCustomerId(id)).build();
        }
        catch(Exception e) {
            return Response.status(400).build();
        }
    }

    /**
     * updates the customer using POST request
     * @param json
     * @return the update response
     */
    @POST
    @Consumes("application/json")
    public Response postCustomer(String json) {
        JSONObject obj = new JSONObject(json);
        String cpr = obj.getString("cpr");
        String name = obj.getString("name");

        if (name == null || cpr == null) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            // String response = sendMessage(message);
            return Response.ok(c.CreateCustomer(cpr, name)).build();
        } catch (Exception e) {
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
            return Response.ok(c.UpdateCustomer(id, cpr, name)).build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }

    /**
     * deletes the customer using customer id
     * @param id
     * @return response about deletion
     */
    @DELETE
    @Path("{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        if (id == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            // String response = sendMessage(message);
            //return Response.ok("Delete customers").build();
            //return Response.status(200).entity(response).build();
            c.DeleteCustomerByCustomerId(id);
            return Response.ok("Deleted customer").status(200).build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }
}

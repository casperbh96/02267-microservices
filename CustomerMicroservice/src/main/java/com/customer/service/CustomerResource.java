package com.customer.service;

import com.customer.Manager.CustomerManager;
import com.customer.Manager.ICustomerManager;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/customer")
@Produces("application/json")
public class CustomerResource {

    private static ICustomerManager c = new CustomerManager();

    @GET
    public Response getCustomers() {
        try {
            return Response.ok(c.getAllCustomers()).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id") int id) {
        if (id == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(c.getCustomerByCustomerId(id)).build();
        }
        catch(Exception e) {
            return Response.status(400).build();
        }
    }

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
            return Response.ok(c.createCustomer(cpr, name)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

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
            return Response.ok(c.updateCustomer(id, cpr, name)).build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }

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
            c.deleteCustomerByCustomerId(id);
            return Response.ok("Deleted customer").status(200).build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }
}

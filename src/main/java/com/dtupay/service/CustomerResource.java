package com.dtupay.service;

import com.dtupay.app.Customer;
import com.dtupay.database.CustomerAdapter;
import com.dtupay.database.ICustomerAdapter;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/customer")
@Produces("application/json")
@Consumes("application/json")
public class CustomerResource {

    private static ICustomerAdapter c = new CustomerAdapter();

    @GET
    public Response getCustomers() {
        try {
            return Response.ok(c.getAllCustomers()).build();
        }
        catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id") int id) {
        if(id == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            // String response = sendMessage(message);
            //return Response.status(200).entity(response).build();
            //return Response.status(200).entity(c.getCustomerByCustomerId(id)).build();
            return Response.ok(c.getCustomerByCustomerId(id)).build();
        }
        catch(Exception e) {
            return Response.serverError().build();
        }
    }

    @POST
    public Response postCustomer(Customer customer) {
        if(customer.getName() == null || customer.getId() == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            // String response = sendMessage(message);
            return Response.ok(c.createCustomer(customer)).build();
        }
        catch(Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    public Response putCustomer(Customer customer) {
        if(customer.getName() == null || customer.getId() == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            return Response.ok(c.updateCustomer(customer)).build();
        }
        catch(Exception e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        if(id == 0) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            // String response = sendMessage(message);
            //return Response.ok("Delete customers").build();
            //return Response.status(200).entity(response).build();
            c.deleteCustomerByCustomerId(id);
            return Response.ok("Deleted customer").status(200).build();
        }
        catch(Exception e){
            return Response.serverError().build();
        }
    }
}

package osa.ora;

import osa.ora.beans.Accounts;
import osa.ora.beans.Customer;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import osa.ora.customer.exception.InvalidRequestException;
import osa.ora.customer.exception.JsonMessage;
import osa.ora.customer.persistence.CustomerAccountsPersistence;
import osa.ora.customer.persistence.CustomerPersistence;

@Path("/V1/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerService {

    private final CustomerPersistence customerPersistence = new CustomerPersistence();
    private final CustomerAccountsPersistence customerAccountPersistence = new CustomerAccountsPersistence();

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer[] getAllCustomers() {
        System.out.println("Load all customers..");
        Customer[] customers=customerPersistence.findAll();
        if(customers!=null && customers.length>0){
            for(Customer customer:customers){
                Accounts[] accounts=customerAccountPersistence.findbyId(customer.getId());
                customer.setCustomerAccounts(accounts);
            }
            return customers;
        }else{
            throw new InvalidRequestException(new JsonMessage("Error", "No Customer Found"));            
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getCustomer(@PathParam("id") String email) {
        Customer customer = customerPersistence.findbyEmail(email);
        if (customer != null) {
            System.out.println("Retireve customer using: " + email);
            Accounts[] accounts=customerAccountPersistence.findbyId(customer.getId());
            customer.setCustomerAccounts(accounts);
            return customer;
        } else {
            throw new InvalidRequestException(new JsonMessage("Error", "Customer "
                    + email + " not found"));
        }
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCustomer(Customer customer) {
        JsonMessage jsonMessage = customerPersistence.save(customer);
        if (jsonMessage.getType().equals("Success")) {
            System.out.println("Successfully added a new user");
            return Response.status(201).build();
        } else {
            throw new InvalidRequestException(jsonMessage);
        }
    }

    @PUT
    @Path("{id}/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(Customer customer, @PathParam("id") long id) {
        customer.setId(id);
        Customer cust = customerPersistence.findbyId(customer.getId());
        if (cust != null) {
            JsonMessage jsm = customerPersistence.update(customer);
            if (jsm.getType().equals("Success")) {
                System.out.println("Successfully updated user with id=" + id);
                return Response.status(Response.Status.OK).build();
            } else {
                throw new InvalidRequestException(jsm);
            }
        } else {
            throw new InvalidRequestException(new JsonMessage("Error", "Customer "
                    + customer.getId() + " not found"));
        }
    }

    @DELETE
    @Path("/remove/{id}")
    public Response deleteCustomer(@PathParam("id") long id) {
        Customer cust = customerPersistence.findbyId(id);
        if (cust != null) {
            JsonMessage jsm = customerPersistence.delete(id);
            if (jsm.getType().equals("Success")) {
                System.out.println("Successfully deleted user with id=" + id);
                return Response.status(Response.Status.OK).build();
            } else {
                throw new InvalidRequestException(jsm);
            }
        } else {
            throw new InvalidRequestException(new JsonMessage("Error", "Customer "
                    + id + " not found"));
        }
    }

}

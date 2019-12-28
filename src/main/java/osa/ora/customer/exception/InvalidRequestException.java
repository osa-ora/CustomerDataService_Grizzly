package osa.ora.customer.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class InvalidRequestException extends WebApplicationException {

    /**
     * Create a HTTP 404 (Not Found) exception.
     */
    public InvalidRequestException() {
        super(Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).build());
    }

    /**
     * Create a HTTP 404 (Not Found) exception.
     * @param message the String that is the entity of the 404 response.
     */
    public InvalidRequestException(String message) {
      super(Response.status(Response.Status.NOT_FOUND).entity(message).type(MediaType.TEXT_PLAIN).build());
    }

    public InvalidRequestException(JsonMessage jse) {
      super(Response.status(Response.Status.NOT_FOUND).entity(jse).type(MediaType.APPLICATION_JSON).build());
    }

}
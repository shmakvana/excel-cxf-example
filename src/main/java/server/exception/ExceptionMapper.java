package server.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {

    public Response toResponse(Exception ex)
    {
        System.out.println("ExceptionMapper: " + ex.getMessage());
        return Response.status(Status.NOT_FOUND).entity("ExceptionMapper").build();
    }
}

package server.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
 
@Provider
public class AppExceptionMapper implements ExceptionMapper<AppException>{

    public Response toResponse(AppException ex)
    {
        System.out.println("AppExceptionMapper: " + ex.getMessage());
        return Response.status(Status.NOT_FOUND).entity("AppExceptionMapper").build();
    }
}

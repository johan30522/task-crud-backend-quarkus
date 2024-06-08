package org.jap.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jap.api.dto.ErrorResponse;

import java.util.List;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException e) {
        System.out.println("entra a la excepcion");
        ErrorResponse message = new ErrorResponse("Illegal Argument", e.getMessage(), List.of());
        return Response.status(Response.Status.NOT_FOUND).entity(message).build();
    }
}

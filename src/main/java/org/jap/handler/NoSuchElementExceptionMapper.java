package org.jap.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jap.api.dto.ErrorResponse;

import java.util.List;
import java.util.NoSuchElementException;

@Provider
public class NoSuchElementExceptionMapper implements ExceptionMapper<NoSuchElementException>{

    @Override
    public Response toResponse(NoSuchElementException exception) {
        ErrorResponse message = new ErrorResponse("Resource not found", exception.getMessage(), List.of());
        return Response.status(Response.Status.NOT_FOUND).entity(message).build();
    }
}

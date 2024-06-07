package org.jap.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.jap.api.dto.ErrorResponse;

import java.util.List;
import java.util.NoSuchElementException;

public class RuntimeExceptionMapper  implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException e) {
        ErrorResponse message = new ErrorResponse("Internal Error", e.getMessage(), List.of());

        return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
}

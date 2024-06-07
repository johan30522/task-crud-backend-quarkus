package org.jap.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    private record ExceptionDto( String error,
                                 String message) {
        public static ExceptionDto encode(int statusCode, Throwable t) {
            return new ExceptionDto(String.valueOf(statusCode), t.getMessage());
        }
    }
    @Override
    public Response toResponse(IllegalArgumentException e) {
        var res = ExceptionDto.encode(422, e);
        return Response.status(Integer.parseInt(res.error)).entity(res).build();
    }
}

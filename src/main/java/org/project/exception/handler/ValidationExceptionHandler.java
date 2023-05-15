package org.project.exception.handler;

import org.project.exception.ValidationException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(ValidationException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", e.getMessage());
        return Response.status(Response
                .Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(result)
                .build();
    }
}

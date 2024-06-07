package org.jap.api.dto;

import java.util.List;

public record ErrorResponse(
        String error,
        String message,
        List<ValidationError> validationErrorList
) {

}

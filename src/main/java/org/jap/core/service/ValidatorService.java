package org.jap.core.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.jap.api.dto.ValidationError;
import org.jap.api.dto.ErrorResponse;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class ValidatorService {

    Validator validator;

    @Inject
    public ValidatorService(Validator validator) {
        this.validator = validator;
    }


    public <T> ErrorResponse validatorDto(T dto){
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        List<ValidationError> validationErrorList=  violations.stream()
                .map(violation -> new ValidationError(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();
        if (validationErrorList.isEmpty()){
            return null;
        }
        return  new ErrorResponse("Validation Error","",validationErrorList);
    }
}

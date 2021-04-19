package jyad.exceptions;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class ErrorResponse {


    private final int status;
    private final String message;
    private final List<ValidationError> validationErrors = new ArrayList<>();


    @RequiredArgsConstructor
    @Getter
    private static final class ValidationError {
        private final String field;
        private final String message;
    }

    public void addValidationError(String field, String message) {
        validationErrors.add(new ValidationError(field, message));
    }
}

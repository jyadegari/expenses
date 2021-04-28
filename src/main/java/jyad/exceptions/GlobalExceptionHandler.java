package jyad.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        var errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error");

        for (var fieldError: ex.getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(errorResponse);
    }


    @ExceptionHandler
    protected ResponseEntity<Object> handleExpenseNotFoundException(ExpenseNotFoundException ex) {

        var errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);

    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        var errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleLuceneIndexingException(LuceneIndexingException ex) {
        var errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleParsDateFromQueryException(ParseDateFromQueryException ex) {
        var errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleCreateCsvFileException(CreateCsvFileException ex) {
        var errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}

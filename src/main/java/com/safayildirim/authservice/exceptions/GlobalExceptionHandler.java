package com.safayildirim.authservice.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error. " +
                "Check 'errors' field for details.");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    @ExceptionHandler(LinkExpiredException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    ResponseEntity<Object> linkExpiredHandler(LinkExpiredException e, WebRequest webRequest) {
        return buildErrorResponse(e, HttpStatus.REQUEST_TIMEOUT, webRequest);
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ResponseEntity<Object> invalidTokenHandler(InvalidTokenException e, WebRequest webRequest) {
        return buildErrorResponse(e, HttpStatus.FORBIDDEN, webRequest);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    ResponseEntity<Object> expiredTokenHandler(ExpiredTokenException e, WebRequest webRequest) {
        return buildErrorResponse(e, HttpStatus.REQUEST_TIMEOUT, webRequest);
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ResponseEntity<Object> usernameTakenHandler(UsernameAlreadyTakenException e, WebRequest webRequest) {
        return buildErrorResponse(e, HttpStatus.CONFLICT, webRequest);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<Object> userNotFoundHandler(UserNotFoundException e, WebRequest webRequest) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<Object> permissionDeniedHandler(PermissionDeniedException e, WebRequest webRequest) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, webRequest);
    }

    @Override
    public ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                          HttpStatus status, WebRequest request) {
        return buildErrorResponse(ex, status, request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception, HttpStatus httpStatus, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), exception.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

}

package com.poc.productservice.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeExceptions(RuntimeException e) {
        return error(INTERNAL_SERVER_ERROR, "Fatal error with the service. Contact support team or please try again later.", e);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ProductServiceException.class})
    public ResponseEntity<Object> handleRestApServiceException(ProductServiceException e) {
        return error(INTERNAL_SERVER_ERROR, "Cannot process due to service error. Contact support team or please try again later.", e, e.getApiSubErrors());
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleExceptions(Exception e) {
        return error(INTERNAL_SERVER_ERROR, "Error occurred while processing request. Contact support team or please try again later.", e);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({UnrecognizedPropertyException.class})
    public ResponseEntity<Object> handleUnrecognizedPropertyException(UnrecognizedPropertyException e) {
        return error(BAD_REQUEST, "Request contains unrecognizable property", e);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return error(BAD_REQUEST, "Bad Request.  Please see errorMessage section for more information.", ex);
    }

    private ResponseEntity<Object> error(HttpStatus status, String message, Exception e) {
        ProductServiceErrorResponse error = buildError(status.getReasonPhrase(), message, e);
        return ResponseEntity.status(status)
                .body(error);
    }

    private ResponseEntity<Object> error(HttpStatus status, String message, Exception e, List<ApiSubError> validationErrors) {
        ProductServiceErrorResponse error = buildError(status.getReasonPhrase(), message, e);
        error.setErrorDetails(validationErrors);
        return ResponseEntity.status(status)
                .body(error);
    }

    private ProductServiceErrorResponse buildError(String statusReason, String message, Exception e) {
        log.error("Responding to API request with Exception : {}", message, e);
        return ProductServiceErrorResponse.builder()
                .dateTime(LocalDateTime.now())
                .status(statusReason)
                .message(message)
                .errorMessage(e.getMessage())
                .build();
    }
}

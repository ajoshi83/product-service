package com.poc.productservice.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ProductServiceException extends Exception {
    private final List<ApiSubError> apiSubErrors = new ArrayList<>();

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public ProductServiceException(String message) {
        super(message);
        log.error(message);
    }

    public ProductServiceException(String message, List<ApiSubError> validationErrorList) {
        super(message);
        this.apiSubErrors.addAll(validationErrorList);
        log.error(message);
    }

    public ProductServiceException(String message, Throwable cause) {
        super(message, cause);
        log.error(message + ":" + cause);
    }

    public ProductServiceException(String message, Throwable cause, List<ApiSubError> validationErrorList) {
        super(message, cause);
        this.apiSubErrors.addAll(validationErrorList);
        log.error(message + ":" + cause);
    }

    public List<ApiSubError> getApiSubErrors() {
        return apiSubErrors;
    }
}

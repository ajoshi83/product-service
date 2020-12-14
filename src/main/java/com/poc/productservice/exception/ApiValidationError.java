package com.poc.productservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Builder
public class ApiValidationError {
    private String field;
    private Object rejectedValue;
    private String message;
}

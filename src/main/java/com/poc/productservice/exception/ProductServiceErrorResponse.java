package com.poc.productservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductServiceErrorResponse implements Serializable {
    @ApiModelProperty("A date and time that the error occurred.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateTime;
    @ApiModelProperty("A status as provided by the HTTP error")
    private String status;
    @ApiModelProperty("A message of the issue")
    private String message;
    @ApiModelProperty("A description of the error")
    private String errorMessage;
    @ApiModelProperty("A description of the error details as a list of errors")
    private List<ApiSubError> errorDetails;
}

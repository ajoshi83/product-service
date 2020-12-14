package com.poc.productservice.api.product.request;

import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CreateUpdateProductRequest implements Serializable {
    @NotEmpty(message = "{sku.not.empty}")
    @Size(max = 12, message = "{sku.maxlength.violation}")
    @ApiModelProperty(required = true)
    private String sku;
    @NotEmpty(message = "{name.not.empty}")
    @Size(max = 50, message = "{name.maxlength.violation}")
    @ApiModelProperty(required = true)
    private String name;
    @NotEmpty(message = "{description.not.empty}")
    @Size(max = 500, message = "{description.maxlength.violation}")
    @ApiModelProperty(required = true)
    private String description;
    @NotEmpty(message = "{manufacturer.not.empty}")
    @Size(max = 50, message = "{manufacturer.maxlength.violation}")
    @ApiModelProperty(required = true)
    private String manufacturer;
    @NotEmpty(message = "{category.not.empty}")
    @Size(max = 30, message = "{category.maxlength.violation}")
    @ApiModelProperty(required = true)
    private String category;
    @Digits(integer = 5, fraction = 2, message = "{costprice.format.violation}")
    @Range(min=0, max=99999, message = "{costprice.invalid.value}")
    @ApiModelProperty(required = true)
    private Double costPrice;
    @Digits(integer = 5, fraction = 2, message = "{sellprice.format.violation}")
    @Range(min=0, max=99999, message = "{sellprice.invalid.value}")
    @ApiModelProperty(required = true)
    private Double sellPrice;
    @ApiModelProperty(required = true, allowableValues = "true, false")
    private boolean active;
}

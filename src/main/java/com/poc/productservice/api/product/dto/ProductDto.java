package com.poc.productservice.api.product.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductDto implements Serializable {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private String manufacturer;
    private String category;
    private Double sellPrice;
}

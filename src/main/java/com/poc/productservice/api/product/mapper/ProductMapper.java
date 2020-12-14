package com.poc.productservice.api.product.mapper;

import com.poc.productservice.api.product.dto.ProductDto;
import com.poc.productservice.api.product.request.CreateUpdateProductRequest;
import com.poc.productservice.model.Product;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {
    Product productRequestToProductEntity(CreateUpdateProductRequest request);
    ProductDto productEntityToProductDto(Product product);
    Product productEntityToProductEntity(Product source);
}

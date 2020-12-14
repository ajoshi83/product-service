package com.poc.productservice.service;

import com.poc.productservice.api.product.dto.ProductDto;
import com.poc.productservice.api.product.request.CreateUpdateProductRequest;

import org.springframework.data.domain.Page;

public interface ProductService {
    Page<ProductDto> getAllProducts(final Integer pageNumber, final String manufacturer, final String sortField, final String sortOrder);
    ProductDto getProductById(final long productId);
    ProductDto createNewProduct(CreateUpdateProductRequest createNewProductRequest);
    ProductDto updateProduct(CreateUpdateProductRequest updateProductRequest, long productId);
    void deleteProduct(final long productId);
}

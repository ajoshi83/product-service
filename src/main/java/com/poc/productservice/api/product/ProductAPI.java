package com.poc.productservice.api.product;

import com.poc.productservice.api.product.dto.ProductDto;
import com.poc.productservice.api.product.request.CreateUpdateProductRequest;
import com.poc.productservice.exception.ProductServiceException;
import com.poc.productservice.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * RestController to handle Product API operations
 *
 * @author Amol Joshi
 */

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Validated
@Api(value = "Product API")
public class ProductAPI {
    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Use this API to create a new product")
    @PostMapping(path = "/v1/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createNewProduct(@Validated @RequestBody final CreateUpdateProductRequest createUpdateProductRequest) throws ProductServiceException {
        log.info("Calling product service to create a new product.");
        ProductDto savedProduct = productService.createNewProduct(createUpdateProductRequest);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @ApiOperation(value = "Use this API to Get products in pagination format")
    @GetMapping(path ="/v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllProducts(@RequestParam(required = false) final Integer page, @RequestParam(required = false) final String manufacturer, @RequestParam(required = false) final String sortField, @RequestParam(required = false) final String sortOrder) {
        log.info("Calling product service to get all active products");
        Page<ProductDto> productDtoPage = productService.getAllProducts(page, manufacturer, sortField, sortOrder);
        return new ResponseEntity<>(productDtoPage, HttpStatus.OK);
    }

    @ApiOperation(value = "Use this API to Get product by product id")
    @GetMapping(path ="/v1/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProductById(@PathVariable final long productId) {
        log.info("Calling product service to get information of product with id {}", productId);
        ProductDto productDto = productService.getProductById(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Use this API to update an existing product")
    @PutMapping(path = "/v1/product/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProduct(@Validated @RequestBody final CreateUpdateProductRequest createUpdateProductRequest, @PathVariable final long productId)  throws ProductServiceException {
        log.info("Calling product service to update product with id {}.", productId);
        ProductDto updatedProduct = productService.updateProduct(createUpdateProductRequest, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @ApiOperation(value = "Use this API to delete an existing product")
    @DeleteMapping(path = "/v1/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteProduct(@PathVariable final long productId)  throws ProductServiceException {
        log.info("Calling product service to delete a product with id {}.", productId);
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package com.poc.productservice.service.impl;

import com.poc.productservice.api.product.dto.ProductDto;
import com.poc.productservice.api.product.mapper.ProductMapper;
import com.poc.productservice.api.product.request.CreateUpdateProductRequest;
import com.poc.productservice.exception.ResourceNotFoundException;
import com.poc.productservice.model.Product;
import com.poc.productservice.repository.ProductRepository;
import com.poc.productservice.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of ProductService
 * @author Amol Joshi
 */

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    /* The ProductMapper to map entity to dto and vice versa */
    private final ProductMapper productMapper;
    /* Repository to handle ORM operations */
    private final ProductRepository productRepository;
    @Value("${products.default.pageSize}")
    private int maxPageSize;
    @Value("${products.default.sort.field.name}")
    private String defaultSortFieldName;

    @Autowired
    public ProductServiceImpl(final ProductRepository productRepository, final ProductMapper productMapper) {
        this.productRepository=productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Create new product
     *
     * @param createUpdateProductRequest
     * @return ProductDTO mapped from the newly created Product
     */
    @Override
    public ProductDto createNewProduct(CreateUpdateProductRequest createUpdateProductRequest) {
        Product productToSave = productMapper.productRequestToProductEntity(createUpdateProductRequest);
        Product savedProduct = productRepository.save(productToSave);
        return productMapper.productEntityToProductDto(savedProduct);
    }

    /**
     * Update the product with given productId
     *
     * @param updateProductRequest
     * @param productId
     * @return ProductDTO mapped from just updated Product
     *
     * @throws ResourceNotFoundException if no product found for given productId
     */
    @Override
    public ProductDto updateProduct(CreateUpdateProductRequest updateProductRequest, long productId)  {
        Product productInDB = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "id", String.valueOf(productId)));
        productInDB.setSku(updateProductRequest.getSku());
        productInDB.setName(updateProductRequest.getName());
        productInDB.setDescription(updateProductRequest.getDescription());
        productInDB.setManufacturer(updateProductRequest.getManufacturer());
        productInDB.setCategory(updateProductRequest.getCategory());
        productInDB.setCostPrice(updateProductRequest.getCostPrice());
        productInDB.setSellPrice(updateProductRequest.getSellPrice());
        productInDB.setActive(updateProductRequest.isActive());
        Product updatedProduct = productRepository.save(productInDB);
        return productMapper.productEntityToProductDto(updatedProduct);
    }

    /**
     * Return all the active products from database
     * Products can be filtered on manufacturer using param manufacturer
     * Products can be sorted on any specific field using param sortField
     * Products can be sorted in ascending/descending order using param sortOrder
     *
     * @param pageNo
     * @param manufacturer
     * @param sortField
     * @param sortOrder
     * @return Page of ProductDtos mapped from all the active Products in the database.
     */
    @Override
    public Page<ProductDto> getAllProducts(Integer pageNo, final String manufacturer, final String sortField, final String sortOrder) {
        Pageable pageable = createPageRequest(pageNo, sortField, sortOrder);
        if(null == manufacturer) {
            return productRepository.findByActiveTrue(pageable).map(productMapper::productEntityToProductDto);
        } else {
            return productRepository.findByActiveTrueAndManufacturerEquals(pageable, manufacturer).map(productMapper::productEntityToProductDto);
        }
    }

    /**
     * Return single product matching with given productId
     *
     * @param productId
     * @return ProductDto mapped from the product with given productId
     *
     * @throws ResourceNotFoundException if no product found with given productId
     */
    @Override
    public ProductDto getProductById(long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "id", String.valueOf(productId)));
        return productMapper.productEntityToProductDto(product);
    }

    /**
     * Helper method to create a Pageable object
     * @param pageNo
     * @param sortField
     * @param sortOrder
     * @return Object of type Pageable
     */
    private Pageable createPageRequest(Integer pageNo, final String sortField, final String sortOrder) {
        int pageNumber = (pageNo == null) ? 0 : pageNo;
        String sortFieldfName = (sortField == null)? defaultSortFieldName: sortField;
        Sort.Direction sortDirection = (sortOrder == null) ? Sort.Direction.ASC: Sort.Direction.fromString(sortOrder);
        return PageRequest.of(pageNumber, maxPageSize, sortDirection, sortFieldfName);
    }

    /**
     * Delete a product with given productId
     *
     * @param productId
     *
     * @throws ResourceNotFoundException if no product found with given productId
     */
    @Override
    public void deleteProduct(long productId) {
        productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "id", String.valueOf(productId)));
        productRepository.deleteById(productId);
    }
}

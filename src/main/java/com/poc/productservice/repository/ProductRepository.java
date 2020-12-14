package com.poc.productservice.repository;

import com.poc.productservice.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Page<Product> findByActiveTrue(final Pageable pageable);
    Page<Product> findByActiveTrueAndManufacturerEquals(final Pageable pageable, final String manufacturer);
}

package com.poc.productservice;

import com.poc.productservice.api.product.dto.ProductDto;
import com.poc.productservice.api.product.mapper.ProductMapper;
import com.poc.productservice.api.product.request.CreateUpdateProductRequest;
import com.poc.productservice.exception.ResourceNotFoundException;
import com.poc.productservice.model.Product;
import com.poc.productservice.repository.ProductRepository;
import com.poc.productservice.service.ProductService;
import com.poc.productservice.service.impl.ProductServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@Slf4j
@PrepareForTest({PageRequest.class, ProductServiceImpl.class})
public class ProductServiceApplicationTests {
	/* Mock for Product Repository */
	@Mock
	private ProductRepository productRepository;
	/* Mock for Product Mapper */
	@Mock
	private ProductMapper productMapper;
	/* Service to be tested */
	private ProductService productService;

	@Before
	public void setup() throws Exception {
		mockStatic(PageRequest.class);
		productService = new ProductServiceImpl(productRepository, productMapper);
	}

	/**
	 * Test method for {@link com.poc.productservice.service.impl.ProductServiceImpl#createNewProduct(CreateUpdateProductRequest)}
	 * Given CreateProductRequest
	 * When createNewProduct is called
	 * THEN new product is created
	 */
	@Test
	public void createNewProduct_Success() {
		CreateUpdateProductRequest createUpdateProductRequest = mock(CreateUpdateProductRequest.class);
		Product productEntityToSave = mock(Product.class);
		/* Constant for a sku number used in test methods */
		String TEST_SKU = "HW-E-NB-RH";
		when(productEntityToSave.getSku()).thenReturn(TEST_SKU);
		Product savedProduct = mock(Product.class);
		when(savedProduct.getSku()).thenReturn(TEST_SKU);
		when(productMapper.productRequestToProductEntity(any(CreateUpdateProductRequest.class))).thenReturn(productEntityToSave);
		when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
		ProductDto productDto = mock(ProductDto.class);
		when(productMapper.productEntityToProductDto(any(Product.class))).thenReturn(productDto);
		when(productDto.getSku()).thenReturn(TEST_SKU);
		ProductDto savedProductDto = productService.createNewProduct(createUpdateProductRequest);
		verify(productRepository).save(productEntityToSave);
		assertEquals(savedProductDto.getSku(), productEntityToSave.getSku());
	}

	/**
	 * Test method for {@link com.poc.productservice.service.impl.ProductServiceImpl#getProductById(long)}
	 * Given a productId
	 * When getProductById is called and product with given id exist
	 * THEN product with give id is returned
	 */
	@Test
	public void getProductById_Success() {
		Product product = mock(Product.class);
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
		ProductDto productDto = mock(ProductDto.class);
		when(productMapper.productEntityToProductDto(any(Product.class))).thenReturn(productDto);
		ProductDto productDtoReceivedFromServiceCall = productService.getProductById(1);
		verify(productRepository).findById(1L);
		assertNotNull(productDtoReceivedFromServiceCall);
	}

	/**
	 * Test method for {@link com.poc.productservice.service.impl.ProductServiceImpl#getProductById(long)}
	 * Given a productId
	 * When getProductById is called and product with given productId does not exist
	 * THEN ResourceNotFoundException is thrown
	 */
	@Test(expected = ResourceNotFoundException.class)
	public void getProductById_ResourceNotFoundException() {
		when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
		productService.getProductById(1);
	}

	/**
	 * Test method for {@link com.poc.productservice.service.impl.ProductServiceImpl#deleteProduct(long)}
	 * Given a productId
	 * When deleteProduct is called and product exist for a given productId
	 * THEN product with give id is deleted
	 */
	@Test
	public void deleteProduct_Success() {
		Product product = mock(Product.class);
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
		productService.deleteProduct(1);
		verify(productRepository).findById(1L);
		verify(productRepository).deleteById(1L);
	}

	/**
	 * Test method for {@link com.poc.productservice.service.impl.ProductServiceImpl#deleteProduct(long)}
	 * Given a productId
	 * When deleteProduct is called and product does not exist for a given productId
	 * THEN ResourceNotFoundException is thrown
	 */
	@Test(expected = ResourceNotFoundException.class)
	public void deleteProduct_ResourceNotFoundException() {
		when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
		productService.getProductById(1);
	}

}

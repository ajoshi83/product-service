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
	@Mock
	private ProductRepository productRepository;
	@Mock
	private ProductMapper productMapper;
	private ProductService productService;

	@Before
	public void setup() throws Exception {
		mockStatic(PageRequest.class);
		productService = new ProductServiceImpl(productRepository, productMapper);
	}

	@Test
	public void createNewProduct_Success() {
		CreateUpdateProductRequest createUpdateProductRequest = mock(CreateUpdateProductRequest.class);
		Product productEntityToSave = mock(Product.class);
		when(productEntityToSave.getSku()).thenReturn("HW-E-NB-RH");
		Product savedProduct = mock(Product.class);
		when(savedProduct.getSku()).thenReturn("HW-E-NB-RH");
		when(productMapper.productRequestToProductEntity(any(CreateUpdateProductRequest.class))).thenReturn(productEntityToSave);
		when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
		ProductDto productDto = mock(ProductDto.class);
		when(productMapper.productEntityToProductDto(any(Product.class))).thenReturn(productDto);
		when(productDto.getSku()).thenReturn("HW-E-NB-RH");
		ProductDto savedProductDto = productService.createNewProduct(createUpdateProductRequest);
		verify(productRepository).save(productEntityToSave);
		assertEquals(savedProductDto.getSku(), productEntityToSave.getSku());
	}

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

	@Test(expected = ResourceNotFoundException.class)
	public void getProductById_ResourceNotFoundException() {
		when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
		productService.getProductById(1);
	}

	@Test
	public void deleteProduct_Success() {
		Product product = mock(Product.class);
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
		productService.deleteProduct(1);
		verify(productRepository).findById(1L);
		verify(productRepository).deleteById(1L);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void deleteProduct_ResourceNotFoundException() {
		when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
		productService.getProductById(1);
	}

}

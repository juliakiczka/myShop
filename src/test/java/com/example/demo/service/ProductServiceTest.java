package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.Product;
import com.example.demo.model.Purchase;
import com.example.demo.repository.JpaProductRepository;
import com.example.demo.repository.JpaPurchaseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private JpaProductRepository productRepository;
    @Mock
    private JpaPurchaseRepository purchaseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts_should_return_list_of_all_products() {
        Product product1 = new Product();
        Product product2 = new Product();
        ArrayList<Product> expectedProducts = new ArrayList<>();

        when(productRepository.findAll()).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getAllProducts();
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void saveProduct_should_add_and_return_new_product() {
        Product product = new Product();
        Mockito.when(productRepository.existsById(Mockito.anyLong())).thenReturn(false);
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        Optional<Product> savedProduct = productService.saveProduct(product);

        Assertions.assertTrue(savedProduct.isPresent());
        assertEquals(product, savedProduct.get());
    }

    @Test
    void getProductById_should_return_product() {
        Product product = new Product();
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));

        Optional<Product> retrievedProduct = productService.getProductById(1L);

        Assertions.assertTrue(retrievedProduct.isPresent());
        assertEquals(product, retrievedProduct.get());
    }


    @Test
    void removeProductById_should_delete_product() {
        Long productId = 1L;
        Product productToRemove = new Product();
        productToRemove.setId(productId);

        doNothing().when(productRepository).deleteById(productId);

        productService.removeProductById(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }


    @Test
    void updateProductById_whenProductExists_should_return_updated_product() {
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("Updated");

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        Optional<Product> result = productService.updateProductById(productId, updatedProduct);

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    void updateProductById_whenProductDoesNotExist_should_return_Empty_Optional() {
        Long productId = 1L;
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("Updated");

        when(productRepository.existsById(productId)).thenReturn(false);

        Optional<Product> result = productService.updateProductById(productId, updatedProduct);

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, never()).save(updatedProduct);
    }

    @Test
    void setPurchases_should_connect_product_with_purchase() {
        Long productId = 1L;
        Long purchaseId = 1L;
        Product product = new Product("Coke", BigDecimal.valueOf(12.50), new ArrayList<>()); // Inicjalizacja listy purchases
        Purchase purchase = new Purchase(LocalDateTime.now(), null, null);

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));

        Optional<Product> result = productService.setPurchases(productId, purchaseId);

        Mockito.verify(productRepository, Mockito.times(1)).save(product);
        assert(result.isPresent());
        assert(result.get() == product);
    }

}
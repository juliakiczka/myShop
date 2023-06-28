package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.Purchase;
import com.example.demo.repository.JpaProductRepository;
import com.example.demo.repository.JpaPurchaseRepository;
import com.example.demo.repository.JpaPurchaseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PurchaseServiceTest {
    @InjectMocks
    private PurchaseService purchaseService;
    @Mock
    private JpaPurchaseRepository purchaseRepository;
    @Mock
    private JpaProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPurchases_should_return_list_of_all_purchases() {
        Purchase purchases1 = new Purchase();
        Purchase purchases2 = new Purchase();
        ArrayList<Purchase> expectedPurchases = new ArrayList<>();

        when(purchaseRepository.findAll()).thenReturn(expectedPurchases);

        List<Purchase> actualPurchases = purchaseService.getAllPurchases();
        assertEquals(expectedPurchases, actualPurchases);
    }

    @Test
    void savePurchase_should_add_and_return_new_Purchase() {
        Purchase purchase = new Purchase();
        Mockito.when(purchaseRepository.existsById(Mockito.anyLong())).thenReturn(false);
        Mockito.when(purchaseRepository.save(Mockito.any(Purchase.class))).thenReturn(purchase);

        Optional<Purchase> savedPurchase = purchaseService.savePurchase(purchase);

        Assertions.assertTrue(savedPurchase.isPresent());
        assertEquals(purchase, savedPurchase.get());
    }

    @Test
    void getPurchaseById_should_return_purchase() {
        Purchase purchase = new Purchase();
        Mockito.when(purchaseRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(purchase));

        Optional<Purchase> retrievedPurchase = purchaseService.getPurchaseById(1L);

        Assertions.assertTrue(retrievedPurchase.isPresent());
        assertEquals(purchase, retrievedPurchase.get());
    }


    @Test
    void removePurchaseById_should_delete_purchase() {
        Long purchaseId = 1L;
        Purchase purchaseToRemove = new Purchase();
        purchaseToRemove.setId(purchaseId);

        doNothing().when(purchaseRepository).deleteById(purchaseId);

        purchaseService.removePurchaseById(purchaseId);

        verify(purchaseRepository, times(1)).deleteById(purchaseId);
    }


    @Test
    void updatePurchaseById_whenPurchaseExists_should_return_updated_purchase() {
        Long purchaseId = 1L;
        Purchase existingPurchase = new Purchase();
        existingPurchase.setId(purchaseId);

        Purchase updatedPurchase = new Purchase();
        updatedPurchase.setId(purchaseId);
        updatedPurchase.setDate(LocalDateTime.now());

        when(purchaseRepository.existsById(purchaseId)).thenReturn(true);
        when(purchaseRepository.save(updatedPurchase)).thenReturn(updatedPurchase);

        Optional<Purchase> result = purchaseService.updatePurchaseById(purchaseId, updatedPurchase);

        verify(purchaseRepository, times(1)).existsById(purchaseId);
        verify(purchaseRepository, times(1)).save(updatedPurchase);
    }

    @Test
    void updatePurchaseById_whenPurchaseDoesNotExist_should_return_Empty_Optional() {
        Long purchaseId = 1L;
        Purchase updatedPurchase = new Purchase();
        updatedPurchase.setId(purchaseId);
        updatedPurchase.setDate(LocalDateTime.now());

        when(purchaseRepository.existsById(purchaseId)).thenReturn(false);

        Optional<Purchase> result = purchaseService.updatePurchaseById(purchaseId, updatedPurchase);

        verify(purchaseRepository, times(1)).existsById(purchaseId);
        verify(purchaseRepository, never()).save(updatedPurchase);
    }


    @Test
    void setProducts_should_connect_purchase_with_product() {
        Long productId = 1L;
        Long purchaseId = 1L;
        Product product = new Product("Coke", BigDecimal.valueOf(12.50), null);
        Purchase purchase = new Purchase(LocalDateTime.now(), null, new ArrayList<>());

        Mockito.when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Purchase> result = purchaseService.setProducts(purchaseId, productId);

        Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
        Mockito.verify(purchaseRepository, Mockito.times(1)).findById(purchaseId);
        Mockito.verify(purchaseRepository, Mockito.times(1)).save(purchase);

        assertTrue(result.isPresent());
        assertEquals(purchase, result.get());
    }
}
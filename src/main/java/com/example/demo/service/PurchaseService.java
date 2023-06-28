package com.example.demo.service;

import com.example.demo.model.Purchase;
import com.example.demo.model.Product;
import com.example.demo.repository.JpaPurchaseRepository;
import com.example.demo.repository.JpaProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {
    @Autowired
    private JpaPurchaseRepository purchaseRepository;
    @Autowired
    private JpaProductRepository productRepository;

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Optional<Purchase> savePurchase(Purchase purchase) {
        if (purchase.getId() != null && purchaseRepository.existsById(purchase.getId())) {
            return Optional.empty();
        }
        return Optional.of(purchaseRepository.save(purchase));
    }

    public Optional<Purchase> getPurchaseById(Long id) {
        return purchaseRepository.findById(id);
    }

    public void removePurchaseById(Long id) {
        purchaseRepository.deleteById(id);
    }

    public Optional<Purchase> updatePurchaseById(Long id, Purchase purchase) {
        if (purchaseRepository.existsById(id)) {
            purchase.setId(id);
            return Optional.of(purchaseRepository.save(purchase));
        }
        return Optional.empty();
    }

    public Optional<Purchase> setProducts(Long purchaseId, Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<Purchase> purchases = purchaseRepository.findById(purchaseId);
        if (product.isPresent() && purchases.isPresent()) {
            purchases.get().addProduct(product.get());
            purchaseRepository.save(purchases.get());
            return purchases;
        }
        return Optional.empty();
    }

}

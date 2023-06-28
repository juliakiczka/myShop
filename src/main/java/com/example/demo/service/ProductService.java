package com.example.demo.service;

import com.example.demo.model.Purchase;
import com.example.demo.model.Product;
import com.example.demo.repository.JpaPurchaseRepository;
import com.example.demo.repository.JpaProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class ProductService {

    @Autowired
    private JpaProductRepository repository;

    @Autowired
    private JpaPurchaseRepository purchaseRepository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Optional<Product> saveProduct(Product product) {
        if (product.getId() != null && repository.existsById(product.getId())) {
            return Optional.empty();
        }
        return Optional.of(repository.save(product));
    }

    public Optional<Product> getProductById(Long id) {
        return repository.findById(id);
    }

    public void removeProductById(Long id) {
        repository.deleteById(id);
    }

    public Optional<Product> updateProductById(Long id, Product product) {
        if (repository.existsById(id)) {
            product.setId(id);
            return Optional.of(repository.save(product));
        }
        return Optional.empty();

    }
//      purchase
    public Optional<Product> setPurchases(Long productId, Long purchaseId) {
        Optional<Product> product = repository.findById(productId);
        Optional<Purchase> purchases = purchaseRepository.findById(purchaseId);
        if (product.isPresent() && purchases.isPresent()) {
            product.get().addOrder(purchases.get());
            repository.save(product.get());
            return product;
        }
        return Optional.empty();
    }
}

package com.example.demo.service;

import com.example.demo.model.Orders;
import com.example.demo.model.Product;
import com.example.demo.repository.JpaOrdersRepository;
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
    private JpaOrdersRepository ordersRepository;

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
//      orders
    public Optional<Product> setOrders(Long productId, Long orderId) {
        Optional<Product> product = repository.findById(productId);
        Optional<Orders> orders = ordersRepository.findById(orderId);
        if (product.isPresent() && orders.isPresent()) {
            product.get().addOrder(orders.get());
            repository.save(product.get());
            return product;
        }
        return Optional.empty();
    }
}

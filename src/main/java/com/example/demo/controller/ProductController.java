package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService service;


    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public List<Product> getAll() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        final Optional<Product> product = service.getProductById(id);
        if (product.isPresent()) {
            log.info("getting client with id {}", id);
            return ResponseEntity.ok().body(product.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.getProductById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        log.info("deleting client with id {}", id);
        service.removeProductById(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping
    public ResponseEntity<Product> post(@RequestBody Product requestProduct) {
        Optional<Product> savedClient = service.saveProduct(requestProduct);
        if (savedClient.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedClient.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PutMapping("/{id}")
    ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> updatedProduct = service.updateProductById(id, product);
        if (!updatedProduct.isEmpty()) {
            return ResponseEntity
                    .ok(updatedProduct.get());
        }
        return ResponseEntity
                .notFound()
                .build();

    }

    @PatchMapping("/{productId}/{orderId}")
    ResponseEntity<Product> patchProductWithOrder(@PathVariable("productId") Long productId, @PathVariable("orderId") Long orderId) {
        Optional<Product> product = service.setOrders(productId, orderId);
        if (product.isPresent()) {
//            log.info("produkt = {}", product);
            return ResponseEntity.status(HttpStatus.CREATED).body(product.get());
        }
        return ResponseEntity.notFound().build();
    }
}

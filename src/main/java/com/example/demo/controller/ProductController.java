package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
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
    private ProductService productService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        final Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            log.info("getting client with id {}", id);
            return ResponseEntity.ok().body(product.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (productService.getProductById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        productService.removeProductById(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping
    public ResponseEntity<Product> post(@Valid @RequestBody Product product) {
        Optional<Product> savedClient = productService.saveProduct(product);
        if (savedClient.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedClient.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PutMapping("/{id}")
    ResponseEntity<Product> update(@PathVariable Long id,@Valid @RequestBody Product product) {
        Optional<Product> updatedProduct = productService.updateProductById(id, product);
        if (!updatedProduct.isEmpty()) {
            return ResponseEntity
                    .ok(updatedProduct.get());
        }
        return ResponseEntity
                .notFound()
                .build();

    }

    //  popraw endpointy
    @PatchMapping("/{productId}/connectWithPurchase/{purchaseId}")
    ResponseEntity<Product> patchProductWithPurchase(@PathVariable("productId") Long productId, @PathVariable("purchaseId") Long purchaseId) {
        Optional<Product> product = productService.setPurchases(
                productId, purchaseId);
        if (product.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(product.get());
        }
        return ResponseEntity.notFound().build();
    }
}

package com.example.demo.controller;

import com.example.demo.model.Purchase;
import com.example.demo.service.ClientService;
import com.example.demo.service.PurchaseService;
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
@RequestMapping("/purchases")
public class PurchaseController {

    private PurchaseService purchaseService;

    private ClientService clientService;


    @Autowired
    public PurchaseController(PurchaseService purchaseService, ClientService clientService) {
        this.purchaseService = purchaseService;
        this.clientService = clientService;
    }

    @GetMapping("/getAll")
    public List<Purchase> getAll() {
        return purchaseService.getAllPurchases();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getById(@PathVariable Long id) {
        final Optional<Purchase> purchase = purchaseService.getPurchaseById(id);
        if (purchase.isPresent()) {
            log.info("getting purchase with id {}", id);
            return ResponseEntity.ok().body(purchase.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (purchaseService.getPurchaseById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (purchaseService.getPurchaseById(id).get().getClient() != null) {
            clientService.disconnectEntitiesPurchaseClient(purchaseService.getPurchaseById(id).get().getClient().getId());
        }
        log.info("deleting purchase with id {}", id);
        purchaseService.removePurchaseById(id);
        return ResponseEntity.ok().build();

    }

    @PostMapping
    public ResponseEntity<Purchase> post(@RequestBody Purchase purchase) {
        Optional<Purchase> savedPurchase = purchaseService.savePurchase(purchase);
        if (savedPurchase.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedPurchase.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PutMapping("/{id}")
    ResponseEntity<Purchase> update(@PathVariable Long id, @RequestBody Purchase purchase) {
        Optional<Purchase> updatedPurchase = purchaseService.updatePurchaseById(id, purchase);
        if (!updatedPurchase.isEmpty()) {
            return ResponseEntity
                    .ok(updatedPurchase.get());
        }
        return ResponseEntity
                .notFound()
                .build();

    }
}

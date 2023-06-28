package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.model.Purchase;
import com.example.demo.service.AddressService;
import com.example.demo.service.ClientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//UPORZĄDKOWAĆ ENDPOINTY (PORZĄDEK TAK JAK W KATALOGACH NA KOMPIE)
@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {
    private ClientService clientService;
    private AddressService addressService;


    @Autowired
    public ClientController(ClientService service, AddressService addressService) {
        this.clientService = service;
        this.addressService = addressService;
    }

    @GetMapping("/getAll")
    public List<Client> getAll() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id) {
        final Optional<Client> client = clientService.getClientById(id);
        if (client.isPresent()) {
            log.info("getting client with id {}", id);
            return ResponseEntity.ok().body(client.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (clientService.getClientById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        log.info("deleting client with id {}", id);
        clientService.removeClientById(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping
    public ResponseEntity<Client> post(@Valid @RequestBody Client client) {
        Optional<Client> savedClient = clientService.saveClient(client);
        if (savedClient.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedClient.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @Valid @RequestBody Client client) {
        Optional<Client> updatedClient = clientService.updateClientById(id, client);
        if (updatedClient.isPresent()) {
            return ResponseEntity
                    .ok(updatedClient.get());
        }
        return ResponseEntity
                .notFound()
                .build();

    }


    @GetMapping("/byName/{clientName}")
    public ResponseEntity<List<Client>> getByName(@PathVariable("clientName") String name) {
        List<Client> clients = clientService.getClientByName(name);
        if (clients.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clients);
    }

    //      address
    @PatchMapping("/{clientId}/connectWithAddress/{addressId}")
    public ResponseEntity<Client> patchClientWithAddress(@PathVariable("clientId") Long clientId, @PathVariable("addressId") Long addressId) {
        Optional<Client> clientOptional = clientService.setAddress(clientId, addressId);
        if (clientOptional.isPresent()) {
            log.info("{} updated", clientOptional);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientOptional.get());
        }
        return ResponseEntity.notFound().build();
    }


    @PatchMapping("/disconnectWithAddress/{clientId}")
    public ResponseEntity<Void> disconnectWithAddress(@PathVariable("clientId") Long clientId) {
        clientService.disconnectEntitiesClientAddress(clientId);
        return ResponseEntity.ok().build();
    }

    //          purchases
    @PatchMapping("/{clientId}/connectWithPurchase/{purchaseId}")
    public ResponseEntity<Purchase> patchClientWithPurchase(@PathVariable("clientId") Long clientId, @PathVariable("purchaseId") Long purchaseId) {
        Optional<Purchase> purchase = clientService.setPurchase(clientId, purchaseId);
        if (purchase.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(purchase.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/disconnectWithPurchase/{purchaseId}")
    public ResponseEntity<Void> disconnectWithPurchases(@PathVariable("purchaseId") Long purchaseId) {
        clientService.disconnectEntitiesPurchaseClient(purchaseId);
        return ResponseEntity.ok().build();
    }

    //    @PatchMapping("/orders/changeOrder/{orderId}/{clientId}")
//    public ResponseEntity<Void> changeOrder(@PathVariable("orderId") Long orderId, @PathVariable("clientId") Long clientId) {
//        boolean b = clientService.changeOrder(orderId, clientId);
//        if (b) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

    @PatchMapping("/disconnectWithAll/{clientId}")
    public ResponseEntity<Void> disconnectWithAll(@PathVariable("clientId") Long clientId) {
        clientService.disconnectWithAllEntities(clientId);
        return ResponseEntity.noContent().build();
//        zamień statusy notFound() na wyjątek
//        throw new ResourceNotFoundException("Client not found with ID: " + clientId);
    }


}
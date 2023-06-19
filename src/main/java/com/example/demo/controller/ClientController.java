package com.example.demo.controller;

import com.example.demo.model.Address;
import com.example.demo.model.Client;
import com.example.demo.service.AddressService;
import com.example.demo.service.ClientService;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {
    private ClientService clientService;
    private AddressService addressService;
//    private EntityManager entityManager;


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
//        if (clientService.getClientById(id).get().getAddress() == null) {
//            entityManager.remove(clientService.getClientById(id));
//            entityManager.flush();
//        }
        log.info("deleting client with id {}", id);
        clientService.removeClientById(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping
    public ResponseEntity<Client> post(@RequestBody Client requestClient) {
        Optional<Client> savedClient = clientService.saveClient(requestClient);
        if (savedClient.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedClient.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PutMapping("/{id}")
    ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client client) {
        Optional<Client> updatedClient = clientService.updateClientById(id, client);
        if (!updatedClient.isEmpty()) {
            return ResponseEntity
                    .ok(updatedClient.get());
        }
        return ResponseEntity
                .notFound()
                .build();

    }

    @PatchMapping("/{clientId}/{addressId}")
    public ResponseEntity<Client> patchClientWithAddress(@PathVariable("clientId") Long clientId, @PathVariable("addressId") Long addressId) {
        Optional<Client> clientOptional = clientService.setAddress(clientId, addressId);
        if (clientOptional.isPresent()) {
            log.info("{} updated", clientOptional);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/disconnect/{clientId}")
    public ResponseEntity<Void> disconnectEntities(@PathVariable("clientId") Long clientId) {
        boolean disconnected = clientService.disconnectEntities(clientId);

        if (disconnected) {
            log.info("entities disconnected");
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }



}
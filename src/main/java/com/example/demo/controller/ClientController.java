package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.service.ClientService;
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
    private ClientService service;


    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public List<Client> getAll() {
        return service.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id) {
        final Optional<Client> client = service.getClientById(id);
        if (client.isPresent()) {
            log.info("getting client with id {}", id);
            return ResponseEntity.ok().body(client.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.getClientById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        log.info("deleting client with id {}", id);
        service.removeClientById(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping
    public ResponseEntity<Client> post(@RequestBody Client requestClient) {
        Optional<Client> savedClient = service.saveClient(requestClient);
        if (savedClient.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedClient.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PutMapping("/{id}")
    ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client client) {
        Optional<Client> updatedClient = service.updateClientById(id, client);
        if (!updatedClient.isEmpty()) {
            return ResponseEntity
                    .ok(updatedClient.get());
        }
        return ResponseEntity
                .notFound()
                .build();

    }
}

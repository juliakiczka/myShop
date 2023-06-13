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

    @PostMapping("")
    public ResponseEntity<Client> postClient(@RequestBody Client requestClient) {
        Client savedClient = service.saveClient(requestClient);
        log.info("{} saved", savedClient);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedClient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        log.info("getting client with id {}", id);
        final Optional<Client> response = service.getById(id);
        return response
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

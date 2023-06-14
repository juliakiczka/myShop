package com.example.demo.controller;

import com.example.demo.model.Address;
import com.example.demo.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/address")
public class AddressController {
    private AddressService service;


    @Autowired
    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public List<Address> getAll() {
        return service.getAllAddresses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getById(@PathVariable Long id) {
        final Optional<Address> address = service.getAddressById(id);
        if (address.isPresent()) {
            log.info("getting address with id {}", id);
            return ResponseEntity.ok().body(address.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.getAddressById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        log.info("deleting address with id {}", id);
        service.removeAddressById(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping
    public ResponseEntity<Address> post(@RequestBody Address requestAddress) {
        Optional<Address> savedAddress = service.saveAddress(requestAddress);
        if (savedAddress.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedAddress.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PutMapping("/{id}")
    ResponseEntity<Address> update(@PathVariable Long id, @RequestBody Address address) {
        Optional<Address> updatedAddress = service.updateAddressById(id, address);
        if (!updatedAddress.isEmpty()) {
            return ResponseEntity
                    .ok(updatedAddress.get());
        }
        return ResponseEntity
                .notFound()
                .build();

    }
}

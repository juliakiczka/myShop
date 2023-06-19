package com.example.demo.controller;

import com.example.demo.model.Address;
import com.example.demo.model.Client;
import com.example.demo.service.AddressService;
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


    @GetMapping("/byName/{clientName}")
    public ResponseEntity<List<Client>> getByName(@PathVariable("clientName") String name) {
        List<Client> clients = clientService.getClientByName(name);
        if (clients.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clients);
    }

    //      address
    @PatchMapping("/{clientId}/{addressId}")
    public ResponseEntity<Client> patchClientWithAddress(@PathVariable("clientId") Long clientId, @PathVariable("addressId") Long addressId) {
        Optional<Client> clientOptional = clientService.setAddress(clientId, addressId);
        if (clientOptional.isPresent()) {
            log.info("{} updated", clientOptional);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientOptional.get());
        }
        return ResponseEntity.notFound().build();
    }


    @PatchMapping("/disconnect/{clientId}")
    public ResponseEntity<Void> disconnectEntities(@PathVariable("clientId") Long clientId) {
        boolean disconnected = clientService.disconnectEntities(clientId);

        if (disconnected) {
            log.info("entities disconnected");
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/addresses/getAll")
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        final Optional<Address> address = addressService.getAddressById(id);
        if (address.isPresent()) {
            log.info("getting address with id {}", id);
            return ResponseEntity.ok().body(address.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Void> deleteAddressById(@PathVariable Long id) {
        if (addressService.getAddressById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (addressService.getAddressById(id).get().getClient() != null) {
            clientService.disconnectEntities(addressService.getAddressById(id).get().getClient().getId());
        }
        log.info("deleting address with id {}", id);
        addressService.removeAddressById(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/addresses")
    public ResponseEntity<Address> postAddress(@RequestBody Address requestAddress) {
        Optional<Address> savedAddress = addressService.saveAddress(requestAddress);
        if (savedAddress.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedAddress.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PutMapping("/addresses/{id}")
    ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        Optional<Address> updatedAddress = addressService.updateAddressById(id, address);
        if (!updatedAddress.isEmpty()) {
            return ResponseEntity
                    .ok(updatedAddress.get());
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    //          orders
//    @PatchMapping("/orders/{clientId}/{orderId}")
//    public ResponseEntity<Client> patchClientWithOrder(@PathVariable("clientId") Long clientId, @PathVariable("orderId") Long orderId) {
//        Optional<Client> clientOptional = clientService.setOrder(clientId, orderId);
//        if (clientOptional.isPresent()) {
//            log.info("{} updated", clientOptional);
//            return ResponseEntity.status(HttpStatus.CREATED).body(clientOptional.get());
//        }
//        return ResponseEntity.notFound().build();
//    }


}
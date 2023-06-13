package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.repository.JpaClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private JpaClientRepository repository;


    public List<Client> getAllClients() {
        return repository.findAll();
    }

    public Client saveClient(Client client) {
        return repository.save(client);
    }

    public Optional<Client> getById(Long id) {
        return repository.findById(id);
    }

    public void removeById(Long id) {
        repository.deleteById(id);
    }

    public Client updateById(Long id, Client client) {
        Optional<Client> optionalClient = repository.findById(id);
        if (optionalClient.isPresent()) {
            Client existingClient = optionalClient.get();
            client.setId(id);
            return repository.save(client);
        }
        return null;

    }
}

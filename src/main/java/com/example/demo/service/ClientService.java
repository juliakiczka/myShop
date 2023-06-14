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

    public Optional<Client> saveClient(Client client) {
        if (client.getId() != null && repository.existsById(client.getId())) {
            return Optional.empty();
        }
        return Optional.of(repository.save(client));

    }


    public Optional<Client> getClientById(Long id) {
        return repository.findById(id);
    }

    public void removeClientById(Long id) {
        repository.deleteById(id);
    }

    public Optional<Client> updateClientById(Long id, Client client) {
        if (repository.existsById(id)) {
            client.setId(id);
            return Optional.of(repository.save(client));
        }
        return Optional.empty();

    }
}

package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.repository.JpaClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        repository.save(client);
        return client;
    }

    public Optional<Client> getById(Long id) {
        return repository.findById(id);
    }

}

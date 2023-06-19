package com.example.demo.service;

import com.example.demo.model.Address;
import com.example.demo.model.Client;
import com.example.demo.repository.JpaAddressRepository;
import com.example.demo.repository.JpaClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.rmi.server.LogStream.log;

@Service
@Slf4j
public class ClientService {
    @Autowired
    private JpaClientRepository clientRepository;
    @Autowired
    private AddressService addressService;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> saveClient(Client client) {
        if (client.getId() != null && clientRepository.existsById(client.getId())) {
            ClientService.log.info("client is not saved (saveClient)");
            return Optional.empty();
        }
        return Optional.of(clientRepository.save(client));

    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public void removeClientById(Long id) {
        clientRepository.deleteById(id);
    }

    public Optional<Client> updateClientById(Long id, Client client) {
        if (clientRepository.existsById(id)) {
            client.setId(id);
            return Optional.of(clientRepository.save(client));
        }
        return Optional.empty();
    }

    public Optional<Client> addNewClient(Client client) {
        if (clientRepository.existsById(client.getId())) {
            return Optional.of(clientRepository.save(client));
        }
        return Optional.empty();
    }

    public Optional<Client> setAddress(Long clientId, Long addressId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Optional<Address> optionalAddress = addressService.getAddressById(addressId);
        if (optionalClient.isPresent() && optionalAddress.isPresent()) {
            Client updatedClient = optionalClient.get();
            updatedClient.setAddress(optionalAddress.get());
            return Optional.of(clientRepository.save(updatedClient));
        }
        return Optional.empty();
    }

    public boolean disconnectEntities(Long clientId) {
        Client client = getClientById(clientId).orElse(null);

        if (client != null) {
            Address address = client.getAddress();

            if (address != null) {
                address.setClient(null);
                client.setAddress(null);

                addressService.save(address);
                clientRepository.save(client);

                return true;
            }
        }

        return false;
    }


}

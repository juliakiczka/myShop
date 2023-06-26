package com.example.demo.service;

import com.example.demo.handler.ResourceNotFoundException;
import com.example.demo.model.Address;
import com.example.demo.model.Client;
import com.example.demo.model.Purchase;
import com.example.demo.repository.JpaAddressRepository;
import com.example.demo.repository.JpaClientRepository;
import com.example.demo.repository.JpaPurchaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClientService {
    //    CAŁA LOGIKA JEST W SERWISIE!
        @Autowired
    private JpaClientRepository clientRepository;
        @Autowired
    private JpaPurchaseRepository purchaseRepository;
        @Autowired
    private JpaAddressRepository addressRepository;

//    @Autowired
//    public ClientService(JpaClientRepository clientRepository, JpaPurchaseRepository purchaseRepository, JpaAddressRepository addressRepository) {
//        this.clientRepository = clientRepository;
//        this.purchaseRepository = purchaseRepository;
//        this.addressRepository = addressRepository;
//    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> saveClient(Client client) {
        if (client.getId() != null && clientRepository.existsById(client.getId())) {
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


    public List<Client> getClientByName(String name) {
        return clientRepository.findAllByNameContaining(name);
    }


//    address

    public Optional<Client> setAddress(Long clientId, Long addressId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalClient.isPresent() && optionalAddress.isPresent()) {
            Client updatedClient = optionalClient.get();
            updatedClient.setAddress(optionalAddress.get());
            return Optional.of(clientRepository.save(updatedClient));
        }
        return Optional.empty();
    }

    //address
    public void disconnectEntitiesClientAddress(Long clientId) {
        Client client = getClientById(clientId).orElse(null);

        if (client != null) {
            Address address = client.getAddress();

            if (address != null) {
                address.setClient(null);
                client.setAddress(null);

                addressRepository.save(address);
                clientRepository.save(client);
            }
        } else {
            throw new ResourceNotFoundException("Client not found with ID: " + clientId);
        }
    }


    //purchase
    public Optional<Purchase> setPurchase(Long clientId, Long purchaseId) {
        Optional<Client> client = clientRepository.findById(clientId);
        Optional<Purchase> purchase = purchaseRepository.findById(purchaseId);
        if (client.isPresent() && purchase.isPresent()) {
            purchase.get().setClient(client.get());
            return Optional.of(purchaseRepository.save(purchase.get()));
        }
        return Optional.empty();
    }

    public void disconnectEntitiesClientPurchase(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElse(null);
        if (purchase != null) {
            Client client = purchase.getClient();
            if (client != null) {
                client.setPurchases(null);
                purchase.setClient(null);
                purchaseRepository.save(purchase);
                clientRepository.save(client);
            }
        } else {
            throw new ResourceNotFoundException("Purchase not found with ID: " + purchaseId);
        }

    }

    //    public boolean changeOrder(Long orderId, Long clientId) {
//        if (disconnectEntitiesClientOrder(orderId)) {
//            Optional<Purchase> optional = setOrder(clientId, orderId);
//            return optional.isPresent();
//        }
//        return false;
//    }

    //    zamień boolean na void
//    tutaj rzuć wyjątek a nie w kontrolerze
    public void disconnectWithAllEntities(Long clientId) {
        Optional<Client> client = getClientById(clientId);
        if (client.isPresent()) {
            Address address = client.get().getAddress();
            if (address != null) {
                client.get().setAddress(null);
                address.setClient(null);
                clientRepository.save(client.get());
                addressRepository.save(address);
            }

            List<Purchase> purchase = client.get().getPurchases();
            if (purchase != null) {
                for (Purchase order : purchase) {
                    order.setClient(null);
                    purchaseRepository.save(order);
                }
                client.get().setPurchases(null);
                clientRepository.save(client.get());
            }
        } else {
            throw new ResourceNotFoundException("Client not found with ID: " + clientId);
        }
    }
}

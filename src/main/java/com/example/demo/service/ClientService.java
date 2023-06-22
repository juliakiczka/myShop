package com.example.demo.service;

import com.example.demo.handler.ResourceNotFoundException;
import com.example.demo.model.Address;
import com.example.demo.model.Client;
import com.example.demo.model.Orders;
import com.example.demo.repository.JpaClientRepository;
import com.example.demo.repository.JpaOrdersRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClientService {
    @Autowired
    private JpaClientRepository clientRepository;
    @Autowired
    private JpaOrdersRepository ordersRepository;
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

    public List<Client> getClientByName(String name) {
        return clientRepository.findAllByNameContaining(name);
    }


//    address

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

    //address
    public boolean disconnectEntitiesClientAddress(Long clientId) {
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


    //order
    public Optional<Orders> setOrder(Long clientId, Long orderId) {
        Optional<Client> client = clientRepository.findById(clientId);
        Optional<Orders> order = ordersRepository.findById(orderId);
        if (client.isPresent() && order.isPresent()) {
            order.get().setClient(client.get());
            return Optional.of(ordersRepository.save(order.get()));
        }
        return Optional.empty();
    }

    public boolean disconnectEntitiesClientOrder(Long orderId) {
        Orders orders = ordersRepository.findById(orderId).orElse(null);
        if (orders != null) {
            Client client = orders.getClient();
            if (client != null) {
                client.setOrders(null);
                orders.setClient(null);
                ordersRepository.save(orders);
                clientRepository.save(client);
                return true;
            }
        }
        return false;
    }

    //    public boolean changeOrder(Long orderId, Long clientId) {
//        if (disconnectEntitiesClientOrder(orderId)) {
//            Optional<Orders> optional = setOrder(clientId, orderId);
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
                addressService.save(address);
            }

            List<Orders> orders = client.get().getOrders();
            if (orders != null) {
                for (Orders order : orders) {
                    order.setClient(null);
                    ordersRepository.save(order);
                }
                client.get().setOrders(null);
                clientRepository.save(client.get());
            }
        } else {
            throw new ResourceNotFoundException("Client not found with ID: " + clientId);
        }
    }


}

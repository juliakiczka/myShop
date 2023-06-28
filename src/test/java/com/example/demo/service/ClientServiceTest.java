package com.example.demo.service;

import com.example.demo.handler.ResourceNotFoundException;
import com.example.demo.model.Address;
import com.example.demo.model.Client;
import com.example.demo.model.Purchase;
import com.example.demo.repository.JpaAddressRepository;
import com.example.demo.repository.JpaClientRepository;
import com.example.demo.repository.JpaPurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private JpaClientRepository clientRepository;

    @Mock
    private JpaPurchaseRepository purchaseRepository;

    @Mock
    private JpaAddressRepository addressRepository;
    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveClient_should_add_and_return_new_client() {
        Client client = new Client();
        Mockito.when(clientRepository.existsById(Mockito.anyLong())).thenReturn(false);
        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        Optional<Client> savedClient = clientService.saveClient(client);

        assertTrue(savedClient.isPresent());
        assertEquals(client, savedClient.get());
    }

    @Test
    void getClientById_should_return_client() {
        Client client = new Client();
        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(client));

        Optional<Client> retrievedClient = clientService.getClientById(1L);

        assertTrue(retrievedClient.isPresent());
        assertEquals(client, retrievedClient.get());
    }

    @Test
    void removeClientById_should_delete_client() {
        Long clientId = 1L;
        Client clientToRemove = new Client();
        clientToRemove.setId(clientId);

        doNothing().when(clientRepository).deleteById(clientId);

        clientService.removeClientById(clientId);

        verify(clientRepository, times(1)).deleteById(clientId);
    }

    @Test
    void getAllClients_should_return_list_of_all_clients() {
        Client client1 = new Client("John", "Doe", "j@d.pl", null, null);
        Client client2 = new Client("Jane", "Doe", "j@d.pl", null, null);
        ArrayList<Client> expectedClients = new ArrayList<>();

        when(clientRepository.findAll()).thenReturn(expectedClients);

        List<Client> actualClients = clientService.getAllClients();
        assertEquals(expectedClients, actualClients);
    }

    @Test
    void getClientByName_should_return_list_of_clients_by_specific_name() {
        Client client1 = new Client("John", "Doe", "j@d.pl", null, null);
        Client client2 = new Client("Johny", "Doe", "j@d.pl", null, null);
        Client client3 = new Client("Jamie", "Doe", "j@d.pl", null, null);
        ArrayList<Client> expectedClients = new ArrayList<>();

        when(clientRepository.findAllByNameContaining("Jo")).thenReturn(expectedClients);

        List<Client> actualClients = clientService.getClientByName("Jo");

        assertEquals(expectedClients, actualClients);
    }

    @Test
    void setAddress_should_connect_address_to_client() {
        Long clientId = 1L;
        Long addressId = 2L;

        Client client = new Client();
        Address address = new Address();

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Optional<Client> result = clientService.setAddress(clientId, addressId);
        assertEquals(client, result.get());

        assertEquals(address, client.getAddress());

        verify(clientRepository, times(1)).findById(clientId);
        verify(addressRepository, times(1)).findById(addressId);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testSetAddress_clientNotFound() {
        Long clientId = 1L;
        Long addressId = 2L;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        Optional<Client> result = clientService.setAddress(clientId, addressId);

        assertFalse(result.isPresent());
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, never()).save(any(Client.class));
    }


    @Test
    void testSetAddress_addressNotFound() {
        Long clientId = 1L;
        Long addressId = 2L;

        Client client = new Client();

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

        Optional<Client> result = clientService.setAddress(clientId, addressId);

        assertFalse(result.isPresent());
        assertNull(client.getAddress());
        verify(clientRepository, times(1)).findById(clientId);
        verify(addressRepository, times(1)).findById(addressId);
        verify(clientRepository, never()).save(any(Client.class));
    }


    @Test
    void disconnectEntitiesClientAddress_clientFound() {
        Long clientId = 1L;
        Client client = new Client();
        Address address = new Address();
        client.setAddress(address);
        address.setClient(client);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        clientService.disconnectEntitiesClientAddress(clientId);

        assertNull(client.getAddress());
        assertNull(address.getClient());

        verify(clientRepository, times(1)).findById(clientId);
        verify(addressRepository, times(1)).save(address);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testDisconnectEntitiesClientAddress_clientNotFound() {
        Long clientId = 1L;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            clientService.disconnectEntitiesClientAddress(clientId);
        });

        verify(clientRepository, times(1)).findById(clientId);
        verifyNoMoreInteractions(addressRepository, clientRepository);
    }

    @Test
    void setPurchase_clientFound() {
        Long clientId = 1L;
        Long purchaseId = 2L;
        Client client = new Client();
        Purchase purchase = new Purchase();

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);

        Optional<Purchase> result = clientService.setPurchase(clientId, purchaseId);

        assertTrue(result.isPresent());
        assertEquals(purchase, result.get());
        assertEquals(client, purchase.getClient());

        verify(clientRepository, times(1)).findById(clientId);
        verify(purchaseRepository, times(1)).findById(purchaseId);
        verify(purchaseRepository, times(1)).save(purchase);
    }

    @Test
    void testSetPurchase_clientNotFound() {
        Long clientId = 1L;
        Long purchaseId = 2L;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.empty());

        Optional<Purchase> result = clientService.setPurchase(clientId, purchaseId);

        assertFalse(result.isPresent());

        verify(clientRepository, times(1)).findById(clientId);
        verify(purchaseRepository, times(1)).findById(purchaseId);
        verify(purchaseRepository, never()).save(any(Purchase.class));
    }


    @Test
    void disconnectEntitiesPurchaseClient_purchaseFound() {
        Long purchaseId = 1L;
        Client client = new Client();
        Purchase purchase = new Purchase();
        client.setPurchases(List.of(purchase));
        purchase.setClient(client);

        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));

        clientService.disconnectEntitiesPurchaseClient(purchaseId);

        assertNull(client.getPurchases());
        assertNull(purchase.getClient());

        verify(purchaseRepository, times(1)).findById(purchaseId);
        verify(clientRepository, times(1)).save(client);
        verify(purchaseRepository, times(1)).save(purchase);
    }

    @Test
    void disconnectWithAllEntities_clientFound() {
        Long clientId = 1L;
        Client client = new Client("Jane", "Doe", "j@d.pl", null, Collections.emptyList());
        Address address = new Address("Nice", "New York", "22-333", null);
        Purchase purchase1 = new Purchase(LocalDateTime.now(), null, null);
        Purchase purchase2 = new Purchase(LocalDateTime.now(), null, null);
        Purchase purchase3 = new Purchase(LocalDateTime.now(), null, null);
        List<Purchase> purchases = List.of(purchase1, purchase2, purchase3);

        client.setId(clientId);
        client.setAddress(address);
        client.setPurchases(purchases);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        clientService.disconnectWithAllEntities(clientId);

        verify(clientRepository, times(2)).save(client);
        verify(addressRepository, times(1)).save(address);
        for (Purchase purchase : purchases) {
            verify(purchaseRepository, times(purchases.size())).save(purchase);
            assertNull(purchase.getClient());
        }
        assertNull(client.getAddress());
        assertNull(client.getPurchases());
    }


    @Test
    void disconnectWithAllEntities_clientNotFound() {
        Long clientId = 1L;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            clientService.disconnectWithAllEntities(clientId);
        });

        verify(clientRepository, times(1)).findById(clientId);
        verifyNoMoreInteractions(addressRepository, clientRepository, purchaseRepository);
    }

}




package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.repository.JpaAddressRepository;
import com.example.demo.repository.JpaClientRepository;
import com.example.demo.repository.JpaPurchaseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        Assertions.assertTrue(savedClient.isPresent());
        assertEquals(client, savedClient.get());
    }

    @Test
    void getClientById_should_return_client() {
        Client client = new Client();
        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(client));

        Optional<Client> retrievedClient = clientService.getClientById(1L);

        Assertions.assertTrue(retrievedClient.isPresent());
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

//    @Test
//     void setAddress_should_connect_address_to_client() {
//         Arrange
//        Long clientId = 1L;
//        Long addressId = 2L;
//
//        Client client = new Client();
//        Address address = new Address();
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
//        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
//        when(clientRepository.save(any(Client.class))).thenReturn(client);
//
//         Act
//        Optional<Client> result = clientService.setAddress(clientId, addressId);

    // Assert
//        assertTrue(result.isPresent());
//        assertEquals(client, result.get());
//        assertEquals(address, client.getAddress());
//
//        verify(clientRepository, times(1)).findById(clientId);
//        verify(addressRepository, times(1)).findById(addressId);
//        verify(clientRepository, times(1)).save(client);
//    }

//    @Test
//     void testSetAddress_ClientNotFound() {
//         Arrange
//        Long clientId = 1L;
//        Long addressId = 2L;
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

    // Act
//        Optional<Client> result = clientService.setAddress(clientId, addressId);

    // Assert
//        assertFalse(result.isPresent());
//        verify(clientRepository, times(1)).findById(clientId);
//        verifyNoInteractions(addressRepository);
//        verifyNoInteractions(clientRepository.save(any(Client.class)));
//    }

//    @Test
//     void testSetAddress_AddressNotFound() {
    // Arrange
//        Long clientId = 1L;
//        Long addressId = 2L;
//
//        Client client = new Client();
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
//        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

    // Act
//        Optional<Client> result = clientService.setAddress(clientId, addressId);
//
    // Assert
//        assertFalse(result.isPresent());
//        assertNull(client.getAddress());
//        verify(clientRepository, times(1)).findById(clientId);
//        verify(addressRepository, times(1)).findById(addressId);
//        verifyNoInteractions(clientRepository.save(any(Client.class)));
//    }


}




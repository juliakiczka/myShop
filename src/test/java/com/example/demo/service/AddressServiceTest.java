package com.example.demo.service;

import com.example.demo.model.Address;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class AddressServiceTest {
    @Mock
    private JpaAddressRepository addressRepository;
    @InjectMocks
    private AddressService addressService;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void getAllAddresses_should_return_list_of_all_clients() {
        Address address1 = new Address("nice", "New York", "44-555", null);
        Address address2 = new Address("ugly", "New York", "44-555", null);
        ArrayList<Address> expectedAddresses = new ArrayList<>();

        when(addressRepository.findAll()).thenReturn(expectedAddresses);

        List<Address> actualAddresses = addressService.getAllAddresses();
        assertEquals(expectedAddresses, actualAddresses);
    }


    @Test
     void saveAddress_should_add_and_return_new_address() {
        Address address = new Address();
        Mockito.when(addressRepository.existsById(Mockito.anyLong())).thenReturn(false);
        Mockito.when(addressRepository.save(Mockito.any(Address.class))).thenReturn(address);

        Optional<Address> savedAddress = addressService.saveAddress(address);

        Assertions.assertTrue(savedAddress.isPresent());
        assertEquals(address, savedAddress.get());
    }

    @Test
     void getAddressById_should_return_address() {
        Address address = new Address();
        Mockito.when(addressRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(address));

        Optional<Address> retrievedAddress = addressService.getAddressById(1L);

        Assertions.assertTrue(retrievedAddress.isPresent());
        assertEquals(address, retrievedAddress.get());
    }

    @Test
     void removeAddressById_should_delete_address() {
        Long addressId = 1L;
        Address addressToRemove = new Address();
        addressToRemove.setId(addressId);

        doNothing().when(addressRepository).deleteById(addressId);

        addressService.removeAddressById(addressId);

        verify(addressRepository, times(1)).deleteById(addressId);
    }

    @Test
     void updateAddressById_whenAddressExists_should_return_updated_address() {
        Long addressId = 1L;
        Address existingAddress = new Address();
        existingAddress.setId(addressId);

        Address updatedAddress = new Address();
        updatedAddress.setId(addressId);
        updatedAddress.setStreet("Updated");

        when(addressRepository.existsById(addressId)).thenReturn(true);
        when(addressRepository.save(updatedAddress)).thenReturn(updatedAddress);

        Optional<Address> result = addressService.updateAddressById(addressId, updatedAddress);

        verify(addressRepository, times(1)).existsById(addressId);
        verify(addressRepository, times(1)).save(updatedAddress);
    }

    @Test
     void updateAddressById_whenAddressDoesNotExist_should_return_Empty_Optional() {
        Long addressId = 1L;
        Address updatedAddress = new Address();
        updatedAddress.setId(addressId);
        updatedAddress.setStreet("Updated");

        when(addressRepository.existsById(addressId)).thenReturn(false);

        Optional<Address> result = addressService.updateAddressById(addressId, updatedAddress);

        verify(addressRepository, times(1)).existsById(addressId);
        verify(addressRepository, never()).save(updatedAddress);
    }
}
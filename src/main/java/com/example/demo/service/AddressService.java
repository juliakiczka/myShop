package com.example.demo.service;

import com.example.demo.model.Address;
import com.example.demo.repository.JpaAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private JpaAddressRepository repository;


    public List<Address> getAllAddresses() {
        return repository.findAll();
    }


    public Optional<Address> saveAddress(Address address) {
        if (address.getId() != null && repository.existsById(address.getId())) {
            return Optional.empty();
        }
        return Optional.of(repository.save(address));
    }

    public Optional<Address> getAddressById(Long id) {

        return repository.findById(id);
    }

    public void removeAddressById(Long id) {
        repository.deleteById(id);
    }

    public Optional<Address> updateAddressById(Long id, Address address) {
        if (repository.existsById(id)) {
            address.setId(id);
            return Optional.of(repository.save(address));
        }
        return Optional.empty();
    }
}

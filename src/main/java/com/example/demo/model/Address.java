package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

//child clienta
@Data
@Entity
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String zipCode;
    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private Client client;

    public Address(String street, String city, String zipCode, Client client) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.client = client;
    }
}

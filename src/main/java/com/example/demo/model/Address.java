package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
//@Table
//@Entity
public class Address {
    @Id
    private Long id;
    private String street;
    private String city;
    private String zipCode;
    private Client client;

}

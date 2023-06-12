package com.example.demo.entity;

import lombok.Data;

@Data
public class Address {
    private Long id;
    private String street;
    private String city;
    private String zipCode;
    private Client client;

}

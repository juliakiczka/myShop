package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotEmpty(message = "Street field cannot be empty!")
    private String street;
    @NotEmpty(message = "City field cannot be empty!")
    private String city;
    @NotEmpty(message = "Zip code field cannot be empty!")
    @Pattern(regexp = "\\d{2}-\\d{3}")
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

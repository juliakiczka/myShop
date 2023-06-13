package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue
    private Long id;
//    @Column(name = "street")

    private String street;
//    @Column(name = "city")
    private String city;
//    @Column(name="zip_code")
    private String zipCode;
//    @OneToOne
//    private Client client;

}

package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private List<Orders> orders;

//          CASE TEST
//    1)dodanie adresu --- działa
//    2)dodanie klienta --- działa
//    3)przypisanie adresu dla klienta --- działa
//    4)zamiana adresu dla klienta --- na patchu adres się nadpisuje
//    5)usunięcie adresu, który ma klienta --- nie chce się usunąć
//    5)usunięcie klienta, który ma adres --- usuwa klienta wraz z adresem

}

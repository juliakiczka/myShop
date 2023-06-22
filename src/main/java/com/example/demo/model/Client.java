package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
//parent addressu
//child orderu
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
    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "orders_id")
    private List<Orders> orders;

//          CASE TEST
//    1)dodanie orderu ---
//    2)dodanie klienta ---
//    3)przypisanie orderu dla klienta --- działa!
//    4)zamiana orderu dla klienta --- metoda changeOrder
//    5)usunięcie orderu, który ma klienta --- usuwa sie order a klient zostaje
//    5)usunięcie klienta, który ma order --- kiedy usuwam klienta to order też sie usuwa
//
}

//patch order dla product
//    patch product dla order
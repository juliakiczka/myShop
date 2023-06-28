package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
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
    @NotEmpty(message = "Name field cannot be empty!")
    private String name;
    @NotEmpty(message = "Surname field cannot be empty!")
    private String surname;
//    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    @Email
    private String email;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    private List<Purchase> purchases;

    public Client(String name, String surname, String email, Address address, List<Purchase> purchases) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.purchases = purchases;
    }
//          CASE TEST
//    1)dodanie orderu ---
//    2)dodanie klienta ---
//    3)przypisanie orderu dla klienta --- działa!
//    4)zamiana orderu dla klienta --- metoda changeOrder
//    5)usunięcie orderu, który ma klienta --- usuwa sie order a klient zostaje
//    5)usunięcie klienta, który ma order --- kiedy usuwam klienta to order też sie usuwa ---- DORÓB METODE!
//

}


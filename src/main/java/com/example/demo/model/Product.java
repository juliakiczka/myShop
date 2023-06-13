package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;
//    @Column(name = "name")
    private String name;
//    @Column(name = "cost")
    private BigDecimal cost;

//    private Category category;

//    private List<Orders> orders;
}
////        Onetoone ---- klient – adres
////
////        Onetomany ---- klient - zamówienie
////
////        Manytoone ---- produkt - kategoria
////
////        Manytomany ---- produkt – zamówienie

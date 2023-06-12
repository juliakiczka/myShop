package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
//@Table
//@Entity
public class Product {
    @Id
    private Long id;
    private String name;
    private BigDecimal cost;
    private Category category;
    private List<Order> orders;
}
//        Onetoone ---- klient – adres
//
//        Onetomany ---- klient - zamówienie
//
//        Manytoone ---- produkt - kategoria
//
//        Manytomany ---- produkt – zamówienie

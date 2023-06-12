package com.example.demo.entity;

import java.math.BigDecimal;
import java.util.List;

public class Product {
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

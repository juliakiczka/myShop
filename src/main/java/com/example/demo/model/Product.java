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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal cost;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "purchase_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "purchase_id"))
    private List<Purchase> orders;

    public void addOrder(Purchase purchase) {
        this.orders.add(purchase);
    }

    public void removeOrder(Purchase purchase) {
        this.orders.remove(purchase);
    }
}
////        Onetoone ---- klient – adres
////
////        Onetomany ---- klient - zamówienie
////
////        Manytoone ---- produkt - kategoria
////
////        Manytomany ---- produkt – zamówienie

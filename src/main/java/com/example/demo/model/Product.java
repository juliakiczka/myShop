package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Name field cannot be empty!")
    private String name;
    @NotEmpty(message = "Cost field cannot be empty!")
    private BigDecimal cost;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "purchase_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "purchase_id"))
    private List<Purchase> purchases;

    public Product(String name, BigDecimal cost, List<Purchase> purchases) {
        this.name = name;
        this.cost = cost;
        this.purchases = purchases;
    }

    public void addOrder(Purchase purchase) {
        this.purchases.add(purchase);
    }

    public void removeOrder(Purchase purchase) {
        this.purchases.remove(purchase);
    }
}
////        Onetoone ---- klient – adres
////
////        Onetomany ---- klient - zamówienie
////
////        Manytoone ---- produkt - kategoria
////
////        Manytomany ---- produkt – zamówienie

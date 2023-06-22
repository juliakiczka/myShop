package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

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

    //    private Category category;
//    @ManyToMany(mappedBy = "products", cascade = CascadeType.MERGE)

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "orders_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "orders_id"))
    private List<Orders> orders;

    public void addOrder(Orders order) {
        this.orders.add(order);
    }

    public void removeOrder(Orders order) {
        this.orders.remove(order);
    }
}
////        Onetoone ---- klient – adres
////
////        Onetomany ---- klient - zamówienie
////
////        Manytoone ---- produkt - kategoria
////
////        Manytomany ---- produkt – zamówienie

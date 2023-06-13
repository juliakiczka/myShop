package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue
    private Long id;
//    @Column(name = "orders_date")
    private LocalDate date;

//    private Client client;

//    private List<Product> products;

}

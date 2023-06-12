package com.example.demo.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
//@Table
public class Order {
    @Id
    private Long id;
    private LocalDate date;
    private Client client;
    private List<Product> products;

}

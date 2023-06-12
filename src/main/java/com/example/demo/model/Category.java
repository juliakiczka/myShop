package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

//@Entity
//@Table
@Data
public class Category {
    @Id
    private Long id;
    private String name;
    private List<Product> products;
}

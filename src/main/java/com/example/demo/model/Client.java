package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.util.List;
@Data
//@Entity
//@Table
public class Client {
    @Id
    private Long id;
    private String name;
    private String surname;
    private Address address;
    private List<Order> orders;

}

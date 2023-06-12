package com.example.demo.entity;

import lombok.Data;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.util.List;
@Data
public class Client {
    private Long id;
    private String name;
    private String surname;
    private Address address;
    private List<Order> orders;

}

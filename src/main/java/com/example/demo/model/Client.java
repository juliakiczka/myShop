package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue
//    @Column(name = "client_id")
    private Long id;
//    @Column(name = "name")

    private String name;
//    @Column(name = "surname")
    private String surname;
//    @Column(name = "email")
    private String email;

//    @OneToOne
//    @JoinColumn(name = "address_id")
//    private Address address;
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "orders_id")
//    private List<Orders> orders;

}

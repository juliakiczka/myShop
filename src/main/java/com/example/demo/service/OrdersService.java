package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Orders;
import com.example.demo.repository.JpaOrdersRepository;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {
    @Autowired
    private JpaOrdersRepository repository;

    public List<Orders> getAllOrders() {
        return repository.findAll();
    }

    public Optional<Orders> saveOrder(Orders order) {
        if (order.getId() != null && repository.existsById(order.getId())) {
            return Optional.empty();
        }
        return Optional.of(repository.save(order));
    }

    public Optional<Orders> getOrderById(Long id) {
        return repository.findById(id);
    }

    public void removeOrderById(Long id) {
        repository.deleteById(id);
    }

    public Optional<Orders> updateOrderById(Long id, Orders order) {
        if (repository.existsById(id)) {
            order.setId(id);
            return Optional.of(repository.save(order));
        }
        return Optional.empty();

    }


}

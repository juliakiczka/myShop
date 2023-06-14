package com.example.demo.repository;

import com.example.demo.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrdersRepository extends JpaRepository<Orders, Long> {
}

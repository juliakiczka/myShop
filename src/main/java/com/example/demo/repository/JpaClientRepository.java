package com.example.demo.repository;

import com.example.demo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaClientRepository extends JpaRepository<Client, Long> {
//    % - dowolny ciąg znaków
    @Query(value = "SELECT c FROM Client c WHERE c.name LIKE %?1%")
    List<Client> findByName(String name);
}

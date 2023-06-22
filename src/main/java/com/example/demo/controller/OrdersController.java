package com.example.demo.controller;

import com.example.demo.model.Orders;
import com.example.demo.service.ClientService;
import com.example.demo.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrdersController {

    private OrdersService service;

    private ClientService clientService;


    @Autowired
    public OrdersController(OrdersService service, ClientService clientService) {
        this.service = service;
        this.clientService = clientService;
    }

    @GetMapping("/getAll")
    public List<Orders> getAll() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getById(@PathVariable Long id) {
        final Optional<Orders> order = service.getOrderById(id);
        if (order.isPresent()) {
            log.info("getting order with id {}", id);
            return ResponseEntity.ok().body(order.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.getOrderById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (service.getOrderById(id).get().getClient() != null) {
            clientService.disconnectEntitiesClientOrder(service.getOrderById(id).get().getClient().getId());
        }
        log.info("deleting order with id {}", id);
        service.removeOrderById(id);
        return ResponseEntity.ok().build();

    }

    @PostMapping
    public ResponseEntity<Orders> post(@RequestBody Orders requestorder) {
        Optional<Orders> savedOrder = service.saveOrder(requestorder);
        if (savedOrder.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedOrder.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
//napraw
//    @PutMapping("/{id}")
//    ResponseEntity<Orders> update(@PathVariable Long id, @RequestBody Orders order) {
//        Optional<Orders> updatedorder = service.updateOrderById(id, order);
//        if (!updatedorder.isEmpty()) {
//            return ResponseEntity
//                    .ok(updatedorder.get());
//        }
//        return ResponseEntity
//                .notFound()
//                .build();
//
//    }
}

package com.company.controller;

import com.company.model.Order;
import com.company.service.interfaces.OrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public HttpEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAll();
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }

    @GetMapping("/customers/{id}/orders")
    public HttpEntity<List<Order>> getAllOrdersByCustomer(@PathVariable("id") String id) {
        List<Order> orders = orderService.getAllByCustomerId(id);
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/orders/{id}")
    public HttpEntity<Order> getOrderById(@PathVariable("id") String id) {
        Order order = orderService.getById(id);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/orders/save")
    public HttpEntity<?> saveOrder(@RequestBody Order order) {
        if (orderService.isExists(order)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            Order savedOrder = orderService.save(order);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/orders/{id}")
                    .buildAndExpand(savedOrder.getId()).toUri();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(location);

            return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
        }
    }

    @PutMapping(value = "/orders/update/{id}")
    public HttpEntity<?> updateOrder(@PathVariable("id") String id, @RequestBody Order order) {
        Order orderToDb = orderService.getById(id);
        if (orderToDb == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            orderToDb.setDate(order.getDate());
            orderToDb.setProducts(order.getProducts());

            orderService.update(orderToDb);
            return new ResponseEntity<>(orderToDb, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/customers/orders/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") String id) {
        orderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/orders/delete-all/")
    public ResponseEntity<?> deleteAll() {
        orderService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

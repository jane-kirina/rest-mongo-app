package com.company.service;

import com.company.model.Order;
import com.company.repository.OrderRepository;
import com.company.service.interfaces.OrderService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getAllByCustomerId(String id) {
        return orderRepository.findByCustomerId(id);
    }

    @Override
    public Order getById(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public boolean isExists(Order entity) {
        return orderRepository.exists(Example.of(entity));
    }

    @Override
    public Order save(Order entity) {
        return orderRepository.save(entity);
    }

    @Override
    public void update(Order entity) {
        orderRepository.save(entity);
    }

    @Override
    public void deleteById(String id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        orderRepository.deleteAll();
    }

}

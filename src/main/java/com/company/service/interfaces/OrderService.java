package com.company.service.interfaces;

import com.company.model.Order;

import java.util.List;

public interface OrderService extends EntityService<Order> {
    List<Order> getAllByCustomerId(String id);
}
